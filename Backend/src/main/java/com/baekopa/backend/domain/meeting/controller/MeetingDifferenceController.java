package com.baekopa.backend.domain.meeting.controller;

import com.baekopa.backend.domain.meeting.dto.response.MeetingDifferenceResponseDTO;
import com.baekopa.backend.domain.meeting.service.MeetingDifferenceService;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MeetingDifferenceController {

    private final MeetingDifferenceService meetingDifferenceService;

    @Operation(summary = "요약 차이 생성", description = "미팅 전체 요약과 내 요약을 비교하여 차이를 생성해줍니다")
    @PostMapping("/studies/{study-id}/meetings/{meeting-id}/difference")
    public ApiResponse<MeetingDifferenceResponseDTO> createDifference(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_DIFFERENCE_CREATE_SUCCESS, meetingDifferenceService.createDifference(meetingId, member));
    }

}
