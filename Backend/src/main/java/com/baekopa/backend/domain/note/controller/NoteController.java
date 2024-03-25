package com.baekopa.backend.domain.note.controller;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.note.dto.request.CreateNoteRequestDto;
import com.baekopa.backend.domain.note.service.NoteService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "새 노트 생성", description = "공부하마 페이지를 통해 노트 생성")
    @PostMapping
    public ApiResponse<Map<String, Long>> createNewNote(@AuthenticationPrincipal Member member, @RequestBody CreateNoteRequestDto requestDto) {

        log.info("새 노트 생성 : {}", requestDto.getTitle());
        Map<String, Long> result = new HashMap<>();

        requestDto.setWriter(member.getId());

        result.put("noteId", noteService.createNewNote(requestDto));
        return ApiResponse.of(SuccessCode.NOTE_CREATE_SUCCESS, result);

    }
}
