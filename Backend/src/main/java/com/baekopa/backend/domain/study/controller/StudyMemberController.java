package com.baekopa.backend.domain.study.controller;

import com.baekopa.backend.domain.member.dto.response.MemberResponseDto;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.dto.StudyMemberDto;
import com.baekopa.backend.domain.study.service.StudyMemberService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    @Operation(summary = "이메일로 멤버(사용자) 조회", description = "이메일에 쿼리 내용이 포함되는 멤버(사용자)를 조회합니다.")
    @GetMapping("/members")
    public ApiResponse<List<MemberResponseDto>> searchMembers(@RequestParam(value = "q") String query, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.SELECT_SUCCESS, studyMemberService.searchMembers(query));
    }

    @Operation(summary = "스터디원 조회", description = "스터디에 속한 멤버를 조회합니다.")
    @GetMapping("/studies/{study-id}/members")
    public ApiResponse<List<StudyMemberDto>> getStudyMembers(@PathVariable(value = "study-id") Long studyId) {
        return ApiResponse.of(SuccessCode.STUDY_MEMBER_GET_SUCCESS, studyMemberService.getStudyMembers(studyId));
    }

}
