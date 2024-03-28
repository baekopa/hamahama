package com.baekopa.backend.domain.meeting.controller;

import com.baekopa.backend.domain.meeting.dto.request.MeetingSummaryUpdateDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingRemindQuizResponseDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingSummaryResponseDTO;
import com.baekopa.backend.domain.meeting.service.MeetingService;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MeetingResultController {
    private final MeetingService meetingService;

    //전문 요약하기
    @Operation(summary = "미팅 전문 요약 생성", description = "미팅 전체문을 요약모델을 통해 요약을 해줍니다")
    @PostMapping("/studies/{study-id}/meetings/{meeting-id}/summary")
    public ApiResponse<MeetingSummaryResponseDTO> createMeetingSummary(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_SUMMARY_CREATE_SUCCESS, meetingService.createSummary(meetingId));
    }

    @Operation(summary = "미팅 전문 요약 조회", description = "미팅 요약을 조회합니다.")
    @GetMapping("/studies/{study-id}/meetings/{meeting-id}/summary")
    public ApiResponse<MeetingSummaryResponseDTO> getMeetingSummary(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_SUMMARY_GET_SUCCESS, meetingService.getMeetingSummary(meetingId));
    }

    @Operation(summary = "미팅 전문 요약 재생성", description = "미팅 전체문을 요약모델을 통해 재요약을 해줍니다")
    @PutMapping("/studies/{study-id}/meetings/{meeting-id}/summary")
    public ApiResponse<MeetingSummaryResponseDTO> updateCreateMeetingSummary(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_SUMMARY_UPDATE_SUCCESS, meetingService.updateCreateSummary(meetingId));
    }

    @Operation(summary = "미팅 전문 요약 수정", description = "미팅 요약문을 수정한 값을 DB에 저장합니다.")
    @PutMapping("/studies/{study-id}/meetings/{meeting-id}/summary-update")
    public ApiResponse<MeetingSummaryResponseDTO> updateMeetingSummary(@RequestBody MeetingSummaryUpdateDTO meetingSummaryUpdateDTO, @PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_SUMMARY_UPDATE_SUCCESS, meetingService.updateMeetingSummary(meetingSummaryUpdateDTO, meetingId));
    }

    @Operation(summary = "미팅 리마인드 퀴즈 생성", description = "미팅 요약을 활용하여 리마인드 퀴즈를 생성합니다.")
    @PostMapping("/studies/{study-id}/meetings/{meeting-id}/remind-quiz")
    public ApiResponse<MeetingRemindQuizResponseDTO> createMeetingRemindQuiz(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member){
        return ApiResponse.of(SuccessCode. MEETING_REMIND_QUIZ_SUCCESS, meetingService.createMeetingRemindQuiz(meetingId));
    }

    @Operation(summary = "미팅 리마인드 퀴즈 재생성", description = "미팅 요약을 활용하여 리마인드 퀴즈를 재생성 합니다.")
    @PutMapping("/studies/{study-id}/meetings/{meeting-id}/remind-quiz")
    public ApiResponse<MeetingRemindQuizResponseDTO>reCreateMeetingRemindQuiz(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member){
        return ApiResponse.of(SuccessCode.MEETING_REMIND_QUIZ_SUCCESS,meetingService.reCreateMeetingRemindQuiz(meetingId));
    }


}
