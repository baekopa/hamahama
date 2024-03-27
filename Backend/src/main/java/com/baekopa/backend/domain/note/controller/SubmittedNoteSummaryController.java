package com.baekopa.backend.domain.note.controller;

import com.baekopa.backend.domain.note.entity.SubmittedNoteSummary;
import com.baekopa.backend.domain.note.service.SubmittedNoteSummaryService;
import com.baekopa.backend.global.response.success.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubmittedNoteSummaryController {

    private final SubmittedNoteSummaryService submittedNoteSummaryService;

    // TODO : 스터디원들의 개인 요약을 요약
//    @Operation(summary = "스터디 원들의 개인 요약을 요약", description = "스터디 미팅 시작전 제출한 스터디 원들의 개인 요약을 요약합니다.")
//    @PostMapping("/notes/submitted-note/summary")
//    public ApiResponse<> createSubmittedNoteSummary(@RequestBody CreateSubmittedNoteSummaryRequestDto requestDto) {
//        return null;
//    }

}
