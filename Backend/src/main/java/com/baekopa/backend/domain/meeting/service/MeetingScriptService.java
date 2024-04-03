package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.request.MeetingScriptRequestDto;
import com.baekopa.backend.domain.meeting.dto.request.MeetingScriptRequestDto.Transcription;
import com.baekopa.backend.domain.meeting.dto.response.MeetingScriptDTO;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingScriptRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.baekopa.backend.global.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingScriptService {

    private final MeetingScriptRepository meetingScriptRepository;
    private final MeetingRepository meetingRepository;
    private final S3UploadService s3UploadService;

    @Value("${BASE_URL_AI}")
    private String fastUrl;

    @Transactional
    public void saveS3(MultipartFile file, Long meetingId) {
        String s3Url = uploadS3RecordFile(file);//s3에 파일 업로드
        saveS3Url(meetingId, s3Url); //s3url 저장
    }

    public String uploadS3RecordFile(MultipartFile file) {
        try {
            return s3UploadService.saveFile("record", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public CompletableFuture<Map<String, Long>> saveMeetingScriptAsync(Long studyId, Long meetingId, MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> saveMeetingScript(studyId, meetingId, file))
                .thenApply(result -> {
                    // 성공 로직 처리
                    log.info("비동기 처리 결과: {}", result);
                    return result;
                })
                .exceptionally(ex -> {
                    // 예외 처리 로직
                    log.error("비동기 처리 중 오류 발생: ", ex);
                    throw new RuntimeException("비동기 처리 중 오류 발생", ex);
                });
    }

    @Transactional
    public Map<String, Long> saveMeetingScript(Long studyId, Long meetingId, MultipartFile file) {
        Map<String, Long> result = new HashMap<>();

        String text = getSpeechToText(studyId, meetingId, file); //fast api로 통신하여 STT 실행 및 텍스트 추출
        Long meetingScriptId = saveScript(meetingId, text);//추출한 script 저장

        result.put("meetingscriptId", meetingScriptId);//script id 저장

        return result;
    }

    @Transactional
    public void saveS3Url(Long meetingId, String s3Url) {
        Meeting meeting = meetingRepository.findByIdAndDeletedAtIsNull(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));
        meeting.updateRecordFile(s3Url);
    }

    @Transactional
    public Long saveScript(Long meetingId, String text) {
        Meeting meeting = meetingRepository.findByIdAndDeletedAtIsNull(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, "유효하지 않은 meetingId"));

        MeetingScript meetingScript = MeetingScript.of(meeting, text);
        meetingScript = meetingScriptRepository.save(meetingScript);

        return meetingScript.getId();
    }

    // 음성 파일을 FastAPI로 전송하는 메소드
    public String getSpeechToText(Long studyId, Long meetingId, MultipartFile file) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String serverUrl = fastUrl + "/studies/transcribe/" + studyId + "/" + meetingId;

            // 파일을 ByteArrayResource로 변환
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename(); // 파일 이름을 제공하기 위해 오버라이드
                }
            };

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", byteArrayResource); // ByteArrayResource를 사용

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<List<Transcription>> response = restTemplate.exchange(
                    serverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<Transcription>>() {
                    }
            );

            List<Transcription> textList = response.getBody();

            StringBuilder sb = new StringBuilder();
            for (MeetingScriptRequestDto.Transcription transcription : textList) {
                String textWithSpaces = transcription.getText().replace("\n", " "); // \n을 띄워쓰기로 바꿈
                sb.append(transcription.getSpeaker()).append("  ").append(textWithSpaces).append("\n");
            }
            String text = sb.toString();
            System.out.println("text = " + text);
            return text; // FastAPI로부터 받은 응답(변환된 텍스트의 리스트)
        } catch (Exception e) {
            e.printStackTrace();
            return "FastAPI로 데이터 전송 및 STT 실패";
        }
    }

    public MeetingScriptDTO getMeetingScript(Long meetingId) {
        Meeting meeting = meetingRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));

        MeetingScript meetingScript = meetingScriptRepository.findByMeetingAndDeletedAtIsNull(meeting)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SCRIPT_NOT_FOUND, ErrorCode.MEETING_SCRIPT_NOT_FOUND.getMessage()));

        return MeetingScriptDTO.from(meetingScript);

    }

    @Transactional
    public MeetingScriptDTO updateMeetingScript(MeetingScriptDTO meetingScriptDTO) {
        MeetingScript meetingScript = meetingScriptRepository.findByIdAndDeletedAtIsNull(meetingScriptDTO.getMeetingScriptId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SCRIPT_NOT_FOUND, ErrorCode.MEETING_SCRIPT_NOT_FOUND.getMessage()));

        meetingScript.updateMeetingScript(meetingScriptDTO.getScriptContent());

        return MeetingScriptDTO.from(meetingScript);
    }
}
