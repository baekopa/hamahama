package com.baekopa.backend.domain.study.controller;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.dto.request.CreateStudyRequestDto;
import com.baekopa.backend.domain.study.dto.request.UpdateStudyInfoRequestDto;
import com.baekopa.backend.domain.study.dto.response.StudyInfoResponseDto;
import com.baekopa.backend.domain.study.service.StudyService;
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
public class StudyController {

    private final StudyService studyService;

    @Operation(summary = "스터디 생성", description = "새로운 스터디를 생성합니다.")
    @PostMapping("/studies")
    public ApiResponse<Map<String, Long>> createNewStudy(@ModelAttribute CreateStudyRequestDto requestDto, @AuthenticationPrincipal Member member) {

        Map<String, Long> result = new HashMap<>();

        Long studyId = studyService.createNewStudy(requestDto, member);
        result.put("studyId", studyId);

        return ApiResponse.of(SuccessCode.STUDY_CREATE_SUCCESS, result);
    }

    @Operation(summary = "스터디 조회", description = "스터디 정보를 조회합니다.")
    @GetMapping("/studies/{study-id}/settings")
    public ApiResponse<StudyInfoResponseDto> getStudyInfo(@PathVariable(value = "study-id") Long studyId) {

        return ApiResponse.of(SuccessCode.STUDY_GET_SUCCESS, studyService.getStudyInfo(studyId));
    }

    @Operation(summary = "스터디 기본 정보 수정", description = "스터디 제목, 내용, 배경사진, 카테고리를 수정합니다.")
    @PutMapping("/studies/{study-id}/settings")
    public ApiResponse<StudyInfoResponseDto> updateStudyBasicInfo(@PathVariable(value = "study-id") Long studyId, @ModelAttribute UpdateStudyInfoRequestDto requestDto) {

        return ApiResponse.of(SuccessCode.STUDY_UPDATE_BASIC_SUCCESS, studyService.updateStudyBasicInfo(studyId, requestDto));
    }

}
