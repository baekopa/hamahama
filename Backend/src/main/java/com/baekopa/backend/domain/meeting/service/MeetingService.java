package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.RemindQuizDTO;
import com.baekopa.backend.domain.meeting.dto.request.CreateMeetingRemindQuizDTO;
import com.baekopa.backend.domain.meeting.dto.request.MeetingSummaryRequestDTO;
import com.baekopa.backend.domain.meeting.dto.request.MeetingSummaryUpdateDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingRemindQuizResponseDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingSummaryDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingSummaryResponseDTO;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingScriptRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingSummaryRepository;
import com.baekopa.backend.domain.meeting.repository.RemindQuizRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.baekopa.backend.global.service.S3UploadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingSummaryRepository meetingSummaryRepository;
    private final MeetingScriptRepository meetingScriptRepository;
    private final RemindQuizRepository remindQuizRepository;
    private final S3UploadService s3UploadService;

    @Value("${BASE_URL_AI}")
    private String fastUrl;
    private final RestTemplate restTemplate;

    public MeetingSummaryResponseDTO createSummary(Long meetingId) {
        String summaryUrl = fastUrl + "/studies/summary";

        String originalText = meetingScriptRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SCRIPT_NOT_EXIST, ErrorCode.MEETING_SCRIPT_NOT_EXIST.getMessage()))
                .getScriptContent();

        originalText = originalText.replace(".", ".\n");

        // Json 변환
        MeetingSummaryRequestDTO meetingSummaryRequestDTO = MeetingSummaryRequestDTO.of(originalText);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;
        try {
            jsonText = objectMapper.writeValueAsString(meetingSummaryRequestDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);

        // db 저장
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_EXIST, ErrorCode.MEETING_NOT_EXIST.getMessage()));
        MeetingSummaryDTO meetingSummaryDTO = restTemplate.postForObject(summaryUrl, requestEntity, MeetingSummaryDTO.class);
        MeetingSummary meetingSummary = MeetingSummary.of(meeting, meetingSummaryDTO.getSummaryText());
        meetingSummaryRepository.save(meetingSummary);
        MeetingSummaryResponseDTO meetingSummaryResponseDTO = MeetingSummaryResponseDTO.from(meetingSummaryDTO);

        return meetingSummaryResponseDTO;
    }

    public MeetingSummaryResponseDTO getMeetingSummary(Long meetingId) {
        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_EXIST, ErrorCode.MEETING_SUMMARY_NOT_EXIST.getMessage()));
        return MeetingSummaryResponseDTO.getMeetingSummary(meetingSummary);
    }

    public MeetingSummaryResponseDTO updateCreateSummary(Long meetingId) {
        String summaryUrl = fastUrl + "/studies/summary";

        String originalText = meetingScriptRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_EXIST, ErrorCode.MEETING_NOT_EXIST.getMessage()))
                .getScriptContent();
        originalText = originalText.replace(".", ".\n");

        // Json 변환
        MeetingSummaryRequestDTO meetingSummaryRequestDTO = MeetingSummaryRequestDTO.of(originalText);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;
        try {
            jsonText = objectMapper.writeValueAsString(meetingSummaryRequestDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);
        MeetingSummaryDTO meetingSummaryDTO = restTemplate.postForObject(summaryUrl, requestEntity, MeetingSummaryDTO.class);

        // db 저장
        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_EXIST, ErrorCode.MEETING_SUMMARY_NOT_EXIST.getMessage()));
        meetingSummary.updateMeetingSummary(meetingSummaryDTO.getSummaryText());

        return MeetingSummaryResponseDTO.from(meetingSummaryDTO);
    }

    public MeetingSummaryResponseDTO updateMeetingSummary(MeetingSummaryUpdateDTO meetingSummaryUpdateDTO, Long meetingId) {
        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_EXIST, ErrorCode.MEETING_SUMMARY_NOT_EXIST.getMessage()));
        meetingSummary.updateMeetingSummary(meetingSummaryUpdateDTO.getSummaryText());

        return MeetingSummaryResponseDTO.getMeetingSummary(meetingSummary);
    }

    public MeetingRemindQuizResponseDTO createMeetingRemindQuiz(Long meetingId) {
        String remindQuizUrl = fastUrl + "/studies/quiz";

        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_EXIST, ErrorCode.MEETING_SUMMARY_NOT_EXIST.getMessage()));

        CreateMeetingRemindQuizDTO createMeetingRemindQuizDTO = CreateMeetingRemindQuizDTO.from(meetingSummary);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;

        try {
            jsonText = objectMapper.writeValueAsString(createMeetingRemindQuizDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);

        MeetingRemindQuizResponseDTO meetingRemindQuizResponseDTO = restTemplate.postForObject(remindQuizUrl, requestEntity, MeetingRemindQuizResponseDTO.class);

        Meeting meeting=meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_EXIST, ErrorCode.MEETING_NOT_EXIST.getMessage()));
        // db 저장
        RemindQuizDTO remindQuizDTO=RemindQuizDTO.of(meetingRemindQuizResponseDTO.getQuiz());
        remindQuizRepository.save(RemindQuiz.from(meeting, remindQuizDTO));

        return meetingRemindQuizResponseDTO;
    }

    public MeetingRemindQuizResponseDTO reCreateMeetingRemindQuiz(Long meetingId){
        String remindQuizUrl = fastUrl + "/studies/quiz";

        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_EXIST, ErrorCode.MEETING_SUMMARY_NOT_EXIST.getMessage()));

        CreateMeetingRemindQuizDTO createMeetingRemindQuizDTO = CreateMeetingRemindQuizDTO.from(meetingSummary);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;

        try {
            jsonText = objectMapper.writeValueAsString(createMeetingRemindQuizDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);

        MeetingRemindQuizResponseDTO meetingRemindQuizResponseDTO = restTemplate.postForObject(remindQuizUrl, requestEntity, MeetingRemindQuizResponseDTO.class);

        // db 저장
        RemindQuiz remindQuiz=remindQuizRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));
        remindQuiz.updateRemindQuiz(meetingRemindQuizResponseDTO.getQuiz());

        return meetingRemindQuizResponseDTO;
    }
}
