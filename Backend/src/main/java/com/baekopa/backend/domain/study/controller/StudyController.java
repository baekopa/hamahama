package com.baekopa.backend.domain.study.controller;

import com.baekopa.backend.domain.study.dto.request.CreateStudyRequestDto;
import com.baekopa.backend.domain.study.service.StudyService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<Map<String, Long>> createNewStudy(@ModelAttribute CreateStudyRequestDto requestDto) {

        Map<String, Long> result = new HashMap<>();

        Long studyId = studyService.createNewStudy(requestDto);
        result.put("studyId", studyId);

        return ApiResponse.of(SuccessCode.CREATE_NEW_STUDY, result);
    }

}
