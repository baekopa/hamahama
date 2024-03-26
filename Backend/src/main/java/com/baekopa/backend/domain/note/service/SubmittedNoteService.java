package com.baekopa.backend.domain.note.service;


import com.baekopa.backend.domain.meeting.dto.response.SharedMeetingDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.note.dto.request.CreateNoteSummaryRequestDto;
import com.baekopa.backend.domain.note.dto.request.CreateNoteSummaryResponseDto;
import com.baekopa.backend.domain.note.dto.request.CreateSubmittedNoteRequestDto;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmittedNoteService {

    private final SubmittedNoteRepository submittedNoteRepository;
    private final NoteRepository noteRepository;
    private final MeetingRepository meetingRepository;
    private final RestTemplate restTemplate;

    @Value("${BASE_URL_AI}")
    private String aiUrl;

    @Transactional
    public List<SharedMeetingDto> createSubmittedNote(Long noteId, CreateSubmittedNoteRequestDto requestDto) throws JsonProcessingException {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND, ErrorCode.NOTE_NOT_FOUND.getMessage()));
        Meeting meeting = meetingRepository.findById(requestDto.getMeetingId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));

        // 이미 내보내기 된 노트와 스터디인지 확인한다.
        if(submittedNoteRepository.existsByNoteAndMeeting(note, meeting)) {
            throw new BusinessException(ErrorCode.NOTE_DUPLICATE_MEETING, ErrorCode.NOTE_DUPLICATE_MEETING.getMessage());
        }

        // 요약이 없다면 요약 생성
        if(note.getSummary() == null) {

            String summaryUrl = aiUrl + "/studies/summary";

            // Json 변환
            CreateNoteSummaryRequestDto summaryRequestDto = CreateNoteSummaryRequestDto.from(note.getContent());
            ObjectMapper objectMapper = new ObjectMapper();
            Object data = objectMapper.writeValueAsString(summaryRequestDto);

            // 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> requestEntity = new HttpEntity<>(data, headers);

            // api 요청
            CreateNoteSummaryResponseDto responseDto = restTemplate.postForObject(summaryUrl, requestEntity, CreateNoteSummaryResponseDto.class);
            note.updateSummary(responseDto.getSummaryText());

            log.info("요약 결과물 : {}", responseDto.getSummaryText());

        }

        // DB 저장
        SubmittedNote submittedNote = SubmittedNote.createSubmittedNote( null, note, meeting);
        submittedNoteRepository.save(submittedNote);

        return submittedNoteRepository.findMeetingByNote(note).stream()
                .map((m) -> SharedMeetingDto.of(m.getMeeting().getId(),
                        m.getMeeting().getTopic(),
                        m.getMeeting().getStudyAt(),
                        m.getMeeting().getStudy().getId(),
                        m.getMeeting().getStudy().getTitle(),
                        m.getMeeting().getStudy().getBackgroundImage())).toList();

    }

}
