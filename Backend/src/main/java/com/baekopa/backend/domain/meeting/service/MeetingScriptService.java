package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.request.MeetingScriptRequestDto;
import com.baekopa.backend.domain.meeting.dto.request.MeetingScriptRequestDto.Transcription;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingScriptRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingScriptService {

    private final MeetingRepository meetingRepository;
    private final MeetingScriptRepository meetingScriptRepository;

    public Long saveScript(Long meetingId, MeetingScriptRequestDto dto) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_VALID_ERROR, "유효하지 않은 meetingId"));
        List<Transcription> transScript = dto.getTranscriptions();
        System.out.println("transScript = " + transScript);
        /**
         * transcript 후처리 후 repository에 저장해야함..
         */
//        MeetingScript meetingScript = Meeting.of(meeting, )
        return meeting.getId();
    }

    // 음성 파일을 FastAPI로 전송하는 메소드
    public List<Transcription> sendFileToFastAPI(Long studyId, Long meetingId, MultipartFile file) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            // FastAPI 서버 URL, 실제 URL로 변경해야 합니다.
            String serverUrl = "http://localhost:8000/stt/transcribe/" + studyId + "/" + meetingId;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<List<Transcription>> response = restTemplate.exchange(
                    serverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<Transcription>>() {}
            );

            return response.getBody(); // FastAPI로부터 받은 응답 처리
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // 실패 시 빈 리스트 반환
        }
    }
}
