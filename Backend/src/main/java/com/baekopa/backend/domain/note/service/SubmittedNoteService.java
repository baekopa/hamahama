package com.baekopa.backend.domain.note.service;


import com.baekopa.backend.domain.meeting.dto.MeetingDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.note.dto.request.CreateNoteSummaryRequestDto;
import com.baekopa.backend.domain.note.dto.request.CreateNoteSummaryResponseDto;
import com.baekopa.backend.domain.note.dto.request.CreateSubmittedNoteRequestDto;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmittedNoteService {

    private final SubmittedNoteRepository submittedNoteRepository;
    private final NoteRepository noteRepository;
    private final MeetingRepository meetingRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public List<MeetingDto> createSubmittedNote(Long noteId, CreateSubmittedNoteRequestDto requestDto) throws JsonProcessingException {

        Note note = noteRepository.findById(noteId).orElseThrow();
        Meeting meeting = meetingRepository.findById(requestDto.getMeetingId()).orElseThrow();

        if(note.getSummary() == null) { // 요약 생성

            String summaryUrl = "http://localhost:8000/studies/meeting";

            // data 전처리
            log.info(note.getContent());
            String content = note.getContent().replaceAll("\\n", "");
            log.info(content);

            // Json 변환
            CreateNoteSummaryRequestDto summaryRequestDto = CreateNoteSummaryRequestDto.from(content);
            ObjectMapper objectMapper = new ObjectMapper();
            Object data = objectMapper.writeValueAsString(summaryRequestDto);

            // 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> requestEntity = new HttpEntity<>(data, headers);

            // api 요청
            CreateNoteSummaryResponseDto responseDto = restTemplate.postForObject(summaryUrl, requestEntity, CreateNoteSummaryResponseDto.class);
            note.updateSummary(responseDto.getSummaryText());

            log.info(responseDto.getSummaryText());

        }

        // DB 저장
        SubmittedNote submittedNote = SubmittedNote.createSubmittedNote(note.getContent(), null, note, meeting);
        submittedNoteRepository.save(submittedNote);

        return submittedNoteRepository.findMeetingByNote(note).stream()
                .map((m) -> MeetingDto.of(m.getId(), m.getTopic(), m.getStudyAt(), m.getStudy().getId(), m.getStudy().getTitle())).toList();

    }

}
