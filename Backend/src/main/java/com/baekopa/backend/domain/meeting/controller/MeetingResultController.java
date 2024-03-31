package com.baekopa.backend.domain.meeting.controller;

import com.baekopa.backend.domain.meeting.dto.request.MeetingSummaryUpdateDTO;
import com.baekopa.backend.domain.meeting.dto.request.UpdateMeetingKeywordListDTO;
import com.baekopa.backend.domain.meeting.dto.response.*;
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

    @Operation(summary = "미팅 리스트 조회", description = "스터디에 속하는 미팅을 모두 조회합니다. 미팅이 진행된 이후의 미팅들 중 최근에 진행된 것 우선으로 정렬하여 출력하였습니다.")
    @GetMapping("/studies/{study-id}/meetings/end")
    public ApiResponse<InStudyMeetingListDTO> getMeetingList(@PathVariable("study-id") Long studyId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_LIST_GET_SUCCESS, meetingService.getMeetingList(studyId));
    }


    @Operation(summary = "미팅에 대한 산출물 세부 내용 조회", description = "선택한 미팅의 모든 산출물(전문, 요약, 리마인드퀴즈, 키워드)을 조회합니다.")
    @GetMapping("/studies/{study-id}/meetings/{meeting-id}/all")
    public ApiResponse<MeetingResponseDTO> getMeetingResultAll(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_RESULT_GET_SUCCESS, meetingService.getMeetingResultAll(meetingId));
    }


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
    public ApiResponse<MeetingRemindQuizResponseDTO> createMeetingRemindQuiz(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_REMIND_QUIZ_SUCCESS, meetingService.createMeetingRemindQuiz(meetingId));
    }

    @Operation(summary = "미팅 리마인드 퀴즈 재생성", description = "미팅 요약을 활용하여 리마인드 퀴즈를 재생성 합니다.")
    @PutMapping("/studies/{study-id}/meetings/{meeting-id}/remind-quiz")
    public ApiResponse<MeetingRemindQuizResponseDTO> reCreateMeetingRemindQuiz(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_REMIND_QUIZ_SUCCESS, meetingService.reCreateMeetingRemindQuiz(meetingId));
    }

    @Operation(summary = "미팅 키워드 생성", description = "미팅 요약을 활용하여 키워드 생성(키워드는 재생성도 이 경로를 사용합니다)")
    @PostMapping("/studies/{study-id}/meetings/{meeting-id}/keyword")
    public ApiResponse<MeetingKeywordListDTO> createMeetingKeyword(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_KEYWORD_CREATE_SUCCESS, meetingService.createMeetingKeyword(meetingId));
    }

    @Operation(summary = "미팅 키워드 조회", description = "미팅 요약을 활용하여 키워드 생성(키워드는 재생성도 이 경로를 사용합니다)")
    @GetMapping("/studies/{study-id}/meetings/{meeting-id}/keyword")
    public ApiResponse<MeetingKeywordListDTO> getMeetingKeyword(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_KEYWORD_CREATE_SUCCESS, meetingService.getMeetingKeyword(meetingId));
    }

    // todo 테스트 다시 확인하기
    @Operation(summary = "미팅 키워드 수정(편집)", description = "미팅 키워드를 수정한 값을 저장합니다.")
    @PutMapping("/studies/{study-id}/meetings/{meeting-id}/keyword")
    public ApiResponse<MeetingKeywordListDTO> updateMeetingKeyword(@RequestBody UpdateMeetingKeywordListDTO updateMeetingKeywordListDTO, @PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_KEYWORD_UPDATE_SUCCESS, meetingService.updateMeetingKeyword(meetingId, updateMeetingKeywordListDTO));
    }

    @Operation(summary = "요약 차이 생성", description = "미팅 전체 요약과 내 요약을 비교하여 차이를 생성해줍니다")
    @PostMapping("/studies/{study-id}/meetings/{meeting-id}/difference")
    public ApiResponse<MeetingDifferenceResponseDTO> createDifference(@PathVariable("study-id") Long studyId, @PathVariable("meeting-id") Long meetingId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.MEETING_DIFFERENCE_CREATE_SUCCESS, meetingService.createDifference(meetingId, member));
    }
}
