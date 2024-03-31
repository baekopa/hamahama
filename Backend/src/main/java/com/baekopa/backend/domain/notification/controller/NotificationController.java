package com.baekopa.backend.domain.notification.controller;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.dto.request.UpdateNotificationEventIdRequestDto;
import com.baekopa.backend.domain.notification.dto.response.NotificationListResponseDto;
import com.baekopa.backend.domain.notification.service.NotificationService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "미확인 알림 목록 조회", description = "인증된 회원에 대한 미확인 알림 목록을 가져옵니다.")
    @GetMapping("/")
    public ApiResponse<NotificationListResponseDto> getNotificationAll(@AuthenticationPrincipal Member member) {

        return ApiResponse.of(SuccessCode.NOTIFICATION_GET_SUCCESS, notificationService.getNotificationList(member));
    }

    @Operation(summary = "미확인 알림 목록 확인 시간 갱신", description = "인증된 회원의 마지막 알림 이벤트 ID를 업데이트하여 알림을 읽음으로 표시합니다.")
    @PutMapping("/")
    public ApiResponse updateLastEventId(@AuthenticationPrincipal Member member, @RequestBody UpdateNotificationEventIdRequestDto requestDto) {

        notificationService.updateLastEventId(member, requestDto.getLastEventId());
        return ApiResponse.of(SuccessCode.NEW_NOTIFICATION_GET_SUCCESS);
    }

    @Operation(summary = "특정 알림 확인", description = "인증된 회원의 특정 알림을 읽은 상태로 변환합니다.")
    @PutMapping("/{notification-id}")
    public ApiResponse readNotification(@AuthenticationPrincipal Member member,
                                        @PathVariable("notification-id") Long notificationId) {
        notificationService.readNotification(member, notificationId);
        return ApiResponse.of(SuccessCode.NOTIFICATION_UPDATE_SUCCESS);
    }
}
