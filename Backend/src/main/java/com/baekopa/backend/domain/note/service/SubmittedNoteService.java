package com.baekopa.backend.domain.note.service;


import com.baekopa.backend.domain.meeting.dto.response.SharedMeetingDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.note.dto.request.CreateSubmittedNoteRequestDto;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmittedNoteService {

    private final SubmittedNoteRepository submittedNoteRepository;
    private final NoteRepository noteRepository;
    private final MeetingRepository meetingRepository;
    private final NoteService noteService;

    @Value("${BASE_URL_AI}")
    private String aiUrl;

    @Transactional
    public List<SharedMeetingDto> createSubmittedNote(Long noteId, CreateSubmittedNoteRequestDto requestDto) throws JsonProcessingException {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND, ErrorCode.NOTE_NOT_FOUND.getMessage()));
        Meeting meeting = meetingRepository.findById(requestDto.getMeetingId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));

        // 이미 내보내기 된 노트와 스터디인지 확인한다.
        if (submittedNoteRepository.existsByNoteAndMeeting(note, meeting)) {
            throw new BusinessException(ErrorCode.NOTE_DUPLICATE_MEETING, ErrorCode.NOTE_DUPLICATE_MEETING.getMessage());
        }

        // 요약이 없다면 요약 생성
        if (note.getSummary() == null) {
            noteService.getNewSummary(note);
        }

        // DB 저장
        SubmittedNote submittedNote = SubmittedNote.createSubmittedNote(null, note, meeting);
        submittedNoteRepository.save(submittedNote);

        return submittedNoteRepository.findByNoteAndDeletedAtIsNull(note).stream()
                .map(m -> SharedMeetingDto.of(m.getMeeting().getId(),
                        m.getMeeting().getTopic(),
                        m.getMeeting().getStudyAt(),
                        m.getMeeting().getStudy().getId(),
                        m.getMeeting().getStudy().getTitle(),
                        m.getMeeting().getStudy().getBackgroundImage())).toList();

    }

}
