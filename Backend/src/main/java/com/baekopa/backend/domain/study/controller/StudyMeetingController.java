package com.baekopa.backend.domain.study.controller;


import com.baekopa.backend.domain.meeting.dto.request.CreateMeetingRequestDto;
import com.baekopa.backend.domain.meeting.dto.request.UpdateMeetingRequestDto;
import com.baekopa.backend.domain.meeting.dto.response.CreateMeetingResponseDto;
import com.baekopa.backend.domain.meeting.dto.response.MeetingListDto;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.dto.response.StudyMeetingResponseDto;
import com.baekopa.backend.domain.study.service.StudyMeetingService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyMeetingController {

    private final StudyMeetingService studyMeetingService;

    @Operation(summary = "새 스터디 미팅 일정 생성", description = "새로운 미팅 일정을 생성합니다.")
    @PostMapping("/{study-id}/meetings")
    public ApiResponse<CreateMeetingResponseDto> createNewMeeting(@PathVariable(name = "study-id") Long studyId, @RequestBody CreateMeetingRequestDto requestDto) {

        log.info(" 새 미팅 생성 : {}", requestDto.getTopic());

        return ApiResponse.of(SuccessCode.MEETING_CREATE_SUCCESS, studyMeetingService.createNewMeeting(studyId, requestDto));

    }

    @Operation(summary = "예정된 스터디 미팅 목록 조회", description = "예정된 스터디 미팅 목록을 조회합니다. 스터디 관리 페이지에서 사용합니다.")
    @GetMapping("/{study-id}/meetings")
    public ApiResponse<List<MeetingListDto>> getScheduledMeeting(@PathVariable(name = "study-id") Long studyId) {

        log.info("에정된 스터디 미팅 목록 조회 : {}", studyId);

        return ApiResponse.of(SuccessCode.MEETING_GET_SUCCESS, studyMeetingService.getScheduledMeeting(studyId));

    }

    @Operation(summary = "스터디 가장 최근 일정 조회", description = "스터디의 가장 최근 일정을 조회합니다.")
    @GetMapping("/{study-id}")
    public ApiResponse<StudyMeetingResponseDto> getStudyMeeting(@PathVariable(value = "study-id") Long studyId) {

        return ApiResponse.of(SuccessCode.STUDY_MEETING_GET_SUCCESS, studyMeetingService.getStudyMeeting(studyId));

    }

    @Operation(summary = "스터디의 예정된 미팅 일정 수정", description = "예정된 미팅 일정을 수정합니다.")
    @PutMapping("/{study-id}/meetings/{meeting-id}")
    public ApiResponse<List<MeetingListDto>> updateMeeting(@PathVariable(name = "study-id") Long studyId, @PathVariable(name = "meeting-id") Long meetingId, @RequestBody UpdateMeetingRequestDto requestDto) {

        log.info("스터디({})의 예정된 미팅({}) 일정 수정", studyId, meetingId);

        return ApiResponse.of(SuccessCode.MEETING_UPDATE_SUCCESS, studyMeetingService.updateMeeting(studyId, meetingId, requestDto));
    }

    @Operation(summary = "스터디 녹음본 다운로드", description = "스터디 녹음본을 다운로드 합니다.")
    @GetMapping("/{study-id}/meetings/{meeting-id}/recordfile")
    public ResponseEntity<UrlResource> getMeetingRecordFile(@PathVariable(name = "study-id") Long studyId, @PathVariable(name = "meeting-id") Long meetingId) {

        return studyMeetingService.getMeetingRecordFile(meetingId);
    }

}
