package com.baekopa.backend.domain.notification.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationListResponseDto {

    private List<NotificationResponseDto> notificationList;

    @Builder
    private NotificationListResponseDto(List<NotificationResponseDto> notificationList) {
        this.notificationList = notificationList;
    }

    public static NotificationListResponseDto from(List<NotificationResponseDto> notificationResponseDto) {
        return builder()
                .notificationList(notificationResponseDto)
                .build();
    }
}