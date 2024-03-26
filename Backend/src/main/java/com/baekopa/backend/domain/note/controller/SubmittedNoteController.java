package com.baekopa.backend.domain.note.controller;

import com.baekopa.backend.domain.meeting.dto.response.SharedMeetingDto;
import com.baekopa.backend.domain.note.dto.request.CreateSubmittedNoteRequestDto;
import com.baekopa.backend.domain.note.service.SubmittedNoteService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class SubmittedNoteController {

    private final SubmittedNoteService submittedNoteService;

    @PostMapping("/{note-id}/meetings")
    public ApiResponse<List<SharedMeetingDto>> createSubmittedNote(@PathVariable(name = "note-id") Long noteId, @RequestBody CreateSubmittedNoteRequestDto requestDto) throws JsonProcessingException {

        log.info(" 노트 공유 : {}, {}", noteId, requestDto.getMeetingId());

        return ApiResponse.of(SuccessCode.NOTE_SHARE_SUCCESS, submittedNoteService.createSubmittedNote(noteId, requestDto));

    }
}
