package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.request.MeetingSummaryRequestDTO;
import com.baekopa.backend.domain.meeting.dto.request.MeetingSummaryUpdateDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingSummaryDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingSummaryResponseDTO;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingScriptRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingSummaryRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingSummaryRepository meetingSummaryRepository;
    private final MeetingScriptRepository meetingScriptRepository;
    private final S3UploadService s3UploadService;

    @Value("${BASE_URL_AI}")
    private String fastUrl;
    private final RestTemplate restTemplate;

    public MeetingSummaryResponseDTO createSummary(Long meetingId) {
        String summaryUrl = fastUrl + "/studies/summary";

        String originalText = meetingScriptRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()))
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
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));
        MeetingSummaryDTO meetingSummaryDTO = restTemplate.postForObject(summaryUrl, requestEntity, MeetingSummaryDTO.class);
        MeetingSummary meetingSummary = MeetingSummary.of(meeting, meetingSummaryDTO.getSummaryText());
        meetingSummaryRepository.save(meetingSummary);
        MeetingSummaryResponseDTO meetingSummaryResponseDTO = MeetingSummaryResponseDTO.from(meetingSummaryDTO);

        return meetingSummaryResponseDTO;
    }

    public MeetingSummaryResponseDTO getMeetingSummary(Long meetingId) {
        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        return MeetingSummaryResponseDTO.getMeetingSummary(meetingSummary);
    }

    public MeetingSummaryResponseDTO updateCreateSummary(Long meetingId) {
        String summaryUrl = fastUrl + "/studies/summary";

        String originalText = meetingScriptRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()))
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
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        meetingSummary.updateMeetingSummary(meetingSummaryDTO.getSummaryText());

        return MeetingSummaryResponseDTO.from(meetingSummaryDTO);
    }

    public MeetingSummaryResponseDTO updateMeetingSummary(MeetingSummaryUpdateDTO meetingSummaryUpdateDTO, Long meetingId){
        MeetingSummary meetingSummary=meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(()->new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        meetingSummary.updateMeetingSummary(meetingSummaryUpdateDTO.getSummaryText());

        return MeetingSummaryResponseDTO.getMeetingSummary(meetingSummary);
    }
}
