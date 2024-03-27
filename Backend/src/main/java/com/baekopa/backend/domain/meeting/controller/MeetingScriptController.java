package com.baekopa.backend.domain.meeting.controller;

import com.baekopa.backend.domain.meeting.dto.request.MeetingScriptRequestDto;
import com.baekopa.backend.domain.meeting.service.MeetingScriptService;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.dto.request.CreateStudyRequestDto;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MeetingScriptController {

    private final MeetingScriptService meetingScriptService;

    @Operation(summary = "스터디 요약", description = "STT 텍스트 파일을 저장합니다.")
    @PostMapping("/studies/{study_id}/meetings/{meeting_id}/record")
    public ApiResponse<Map<String, Long>> saveMeetingScript(@PathVariable(value="study-id") Long studyId,
                                                            @PathVariable(value="meeting-id") Long meetingId,
                                                            @RequestBody MeetingScriptRequestDto scriptRequestDto) {

        Map<String, Long> result = new HashMap<>();

        System.out.println("studyId = " + studyId);
        System.out.println("meeting_id = " + meetingId);
        System.out.println("scriptRequestDto = " + scriptRequestDto);

        Long meetingScriptId = meetingScriptService.saveScript(meetingId, scriptRequestDto);
        result.put("meetingScriptId", meetingScriptId);

        return ApiResponse.of(SuccessCode.STUDY_CREATE_SUCCESS, result);
    }
}
