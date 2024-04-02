package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.response.DifferenceTextListDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingDifferenceResponseDTO;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingSummaryRepository;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingDifferenceService {

    private final MeetingSummaryRepository meetingSummaryRepository;
    private final SubmittedNoteRepository submittedNoteRepository;
    private final MeetingRepository meetingRepository;

    @Value("${BASE_URL_AI}")
    private String fastUrl;
    private final RestTemplate restTemplate;

    public MeetingDifferenceResponseDTO createDifference(Long meetingId, Member member) {

        // SubmittedNote 조회
        SubmittedNote submittedNote = getSubmittedNote(meetingId, member);

        // 내 요약문
        String myNoteSummary = submittedNote.getNote().getSummary();

        // 전체 요약문
        String meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()))
                .getSummaryContent();

        // 두 요약문 함께 전달
        List<String> submittedNoteList = Arrays.asList(myNoteSummary, meetingSummary);

        DifferenceTextListDTO differenceTextListDTO = DifferenceTextListDTO.of(submittedNoteList);
        String requestBody = convertObjectToJson(differenceTextListDTO);

        String differenceUrl = fastUrl + "/studies/difference";

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        MeetingDifferenceResponseDTO meetingDifferenceResponseDTO =
                restTemplate.postForObject(differenceUrl, requestEntity, MeetingDifferenceResponseDTO.class);

        submittedNote.updateDifferenceContent(meetingDifferenceResponseDTO.getDifference());

        return meetingDifferenceResponseDTO;
    }

    public MeetingDifferenceResponseDTO getDifference(Long meetingId, Member member) {

        // SubmittedNote 조회
        SubmittedNote submittedNote = getSubmittedNote(meetingId, member);

        String differenceContent = submittedNote.getDifferenceContent();

        MeetingDifferenceResponseDTO meetingDifferenceResponseDTO = MeetingDifferenceResponseDTO.of(differenceContent);

        return meetingDifferenceResponseDTO;
    }

    private SubmittedNote getSubmittedNote(Long meetingId, Member member) {

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));

        // Member와 Meeting을 기준으로 SubmittedNote 조회
        return submittedNoteRepository.findAllByMeetingAndDeletedAtIsNull(meeting)
                .stream()
                .filter(submittedNote -> submittedNote.getNote().getMember().getId() == member.getId())
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND, ErrorCode.NOTE_NOT_FOUND.getMessage()));
    }

    private String convertObjectToJson(Object object) {

        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PARSE_ERROR, ErrorCode.JSON_PARSE_ERROR.getMessage());
        }
    }
}
