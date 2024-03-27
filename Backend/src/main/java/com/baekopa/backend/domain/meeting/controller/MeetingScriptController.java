package com.baekopa.backend.domain.meeting.controller;

import com.baekopa.backend.domain.meeting.dto.request.MeetingScriptRequestDto;
import com.baekopa.backend.domain.meeting.dto.request.MeetingScriptRequestDto.Transcription;
import com.baekopa.backend.domain.meeting.service.MeetingScriptService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MeetingScriptController {
    private static final Logger logger = LoggerFactory.getLogger(MeetingScriptController.class);
    private final MeetingScriptService meetingScriptService;


    @Operation(summary = "스터디 요약", description = "STT 텍스트 파일을 저장합니다.")
    @PostMapping("/studies/{studyId}/meetings/{meetingId}/record")
    public ApiResponse<Map<String, Long>> saveMeetingScript(@PathVariable(value="studyId") Long studyId,
                                                            @PathVariable(value="meetingId") Long meetingId,
                                                            @RequestParam("file") MultipartFile file) {
        logger.info("POST 요청을 받았습니다. studyId: {}, meetingId: {}", studyId, meetingId);

        // 파일을 처리하고, FastAPI로 전송하는 로직 구현
        Map<String, Long> result = new HashMap<>();

        List<Transcription> response = meetingScriptService.sendFileToFastAPI(studyId, meetingId, file);
        System.out.println("response = " + response);

        return ApiResponse.of(SuccessCode.STUDY_CREATE_SUCCESS, result);
    }
}
