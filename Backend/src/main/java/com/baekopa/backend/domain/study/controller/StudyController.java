package com.baekopa.backend.domain.study.controller;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.dto.request.CreateStudyRequestDto;
import com.baekopa.backend.domain.study.dto.request.UpdateStudyInfoRequestDto;
import com.baekopa.backend.domain.study.dto.response.StudyInfoResponseDto;
import com.baekopa.backend.domain.study.service.StudyService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    // 새로운 스터디 생성
    // JWT 토큰으로 인증하는 내용 필요.
    @PostMapping("/studies/new")
    public ApiResponse<Map<String, Long>> createNewStudy(@ModelAttribute CreateStudyRequestDto requestDto, @AuthenticationPrincipal Member member) {

        Map<String, Long> result = new HashMap<>();

        Long studyId = studyService.createNewStudy(requestDto, member);
        result.put("studyId", studyId);

        return ApiResponse.of(SuccessCode.STUDY_CREATE_SUCCESS, result);
    }

    // 스터디 정보 조회 (스터디 관리 페이지)
    @GetMapping("/studies/{study-id}/settings")
    public ApiResponse<StudyInfoResponseDto> getStudyInfo(@PathVariable(value = "study-id") Long studyId) {

        return ApiResponse.of(SuccessCode.STUDY_GET_SUCCESS, studyService.getStudyInfo(studyId));
    }

    // 스터디 기본 정보 수정
    @PutMapping("/studies/{study-id}/settings")
    public ApiResponse<StudyInfoResponseDto> updateStudyBasicInfo(@PathVariable(value = "study-id") Long studyId, @ModelAttribute UpdateStudyInfoRequestDto requestDto) {

        return ApiResponse.of(SuccessCode.STUDY_UPDATE_BASIC_SUCCESS, studyService.updateStudyBasicInfo(studyId, requestDto));
    }

}
