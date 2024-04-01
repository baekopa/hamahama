package com.baekopa.backend.domain.notification.controller;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.service.EmitterService;
import com.baekopa.backend.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/sse")
@RequiredArgsConstructor
public class EmitterController {
    private final EmitterService emitterService;

    @Operation(summary = "SSE 연결", description = "Server-Sent Events (SSE)를 사용하여 알림을 구독합니다.")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal Member member, HttpServletResponse response) {

        response.setHeader("X-Accel-Buffering", "no");
        return emitterService.subscribe(member);
    }
}
