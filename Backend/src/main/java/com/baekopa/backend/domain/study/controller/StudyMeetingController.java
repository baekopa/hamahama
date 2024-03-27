package com.baekopa.backend.domain.study.controller;


import com.baekopa.backend.domain.study.service.StudyMeetingService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyMeetingController {

    private final StudyMeetingService studyMeetingService;

    @Operation(summary = "예정된 스터디 미팅 목록 조회", description = "예정된 스터디 미팅 목록을 조회합니다. 스터디 관리 페이지에서 사용합니다.")
    @GetMapping("/{study-id}/meetings")
    public ApiResponse<?> getScheduledMeeting(@PathVariable(name = "study-id") Long studyId) {

        log.info("에정된 스터디 미팅 목록 조회 : {}", studyId);

        return ApiResponse.of(SuccessCode.MEETING_GET_SUCCESS, studyMeetingService.getScheduledMeeting(studyId));

    }

}
