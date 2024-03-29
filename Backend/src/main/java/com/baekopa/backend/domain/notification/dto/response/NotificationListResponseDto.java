package com.baekopa.backend.domain.notification.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationListResponseDto {

    private List<NotificationResponseDto> notificationList;
    private int numOfUncheckedNotification;
    private String lastNotificationEventId;

    @Builder
    private NotificationListResponseDto(List<NotificationResponseDto> notificationList, int numOfUncheckedNotification, String lastNotificationEventId) {
        this.notificationList = notificationList;
        this.numOfUncheckedNotification = numOfUncheckedNotification;
        this.lastNotificationEventId = lastNotificationEventId;
    }

    public static NotificationListResponseDto from(List<NotificationResponseDto> notificationResponseDto, String lastNotificationEventId) {
        return builder()
                .notificationList(notificationResponseDto)
                .numOfUncheckedNotification(notificationResponseDto.size())
                .lastNotificationEventId(lastNotificationEventId)
                .build();
    }
}