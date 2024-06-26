package com.baekopa.backend.domain.notification.dto.response;

import com.baekopa.backend.domain.notification.entity.Notification;
import com.baekopa.backend.domain.notification.entity.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationResponseDto {

    private Long notificationId;
    private String notificationContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String relatedContentId;
    private NotificationType notificationType;

    @Builder
    private NotificationResponseDto(Long notificationId, String notificationContent, LocalDateTime createdAt, String relatedContentId, NotificationType notificationType) {
        this.notificationId = notificationId;
        this.notificationContent = notificationContent;
        this.createdAt = createdAt;
        this.relatedContentId = relatedContentId;
        this.notificationType = notificationType;
    }

    public static NotificationResponseDto of(Notification notification) {

        return builder()
                .notificationId(notification.getId())
                .notificationContent(notification.getNotificationContent())
                .createdAt(notification.getCreatedAt())
                .relatedContentId(notification.getRelatedContentId())
                .notificationType(notification.getNotificationType())
                .build();
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
}