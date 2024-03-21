package com.baekopa.backend.domain.member.controller;

import com.baekopa.backend.domain.member.dto.request.MyInfoReqeustDto;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.service.MemberService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/members/me")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 정보 조회", description = "사용자의 정보를 조회합니다.")
    @GetMapping()
    public ApiResponse getMyInfo(@AuthenticationPrincipal Member member) {

        log.info(" 내 정보 조회 : {}", member.getEmail());

        return ApiResponse.of(SuccessCode.MEMBER_FIND_SUCCESS, memberService.getMyInfo(member));
    }

    @Operation(summary = "내 정보 수정", description = "사용자의 프로필 사진, 이름을 수정합니다.")
    @PutMapping()
    public ApiResponse updateMyInfo(@AuthenticationPrincipal Member member, @ModelAttribute MyInfoReqeustDto myInfoReqeustDto) throws IOException{

        log.info(" 내 정보 수정 : {}", member.getEmail());
        return ApiResponse.of(SuccessCode.UPDATE_SUCCESS, memberService.updateMyInfo(member, myInfoReqeustDto));
    }
}
