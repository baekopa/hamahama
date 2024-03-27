package com.baekopa.backend.domain.meeting.controller;

import com.baekopa.backend.domain.meeting.dto.request.CreateMeetingRequestDto;
import com.baekopa.backend.domain.meeting.service.MeetingService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @Operation(summary = "새 스터디 미팅 일정 생성", description = "새로운 미팅 일정을 생성합니다.")
    @PostMapping("/")
    public ApiResponse<?> createNewMeeting(@RequestBody CreateMeetingRequestDto requestDto) {

        log.info(" 새 미팅 생성 : {}", requestDto.getTopic());

        meetingService.createNewMeeting(requestDto);

        return ApiResponse.of(SuccessCode.MEETING_CREATE_SUCCESS, null);

    }
}
