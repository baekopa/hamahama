package com.baekopa.backend.domain.study.controller;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.entity.dto.response.MemberResponseDto;
import com.baekopa.backend.domain.study.service.StudyMemberService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    @GetMapping("/members")
    public ApiResponse<List<MemberResponseDto>> searchMembers(@RequestParam(value = "q") String query, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.SELECT_SUCCESS, studyMemberService.searchMembers(query));
    }
}
