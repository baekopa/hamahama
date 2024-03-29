package com.baekopa.backend.domain.notification.dto;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.entity.Notification;
import com.baekopa.backend.domain.notification.entity.NotificationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationDto {

    private Member receiver;
    private NotificationType notificationType;
    private String notificationContent;
    private Long relatedContentId;

    @Builder
    private NotificationDto(Member receiver, NotificationType notificationType, String notificationContent, Long relatedContentId) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
        this.relatedContentId = relatedContentId;
    }

    public static NotificationDto of(Member receiver, NotificationType notificationType, String notificationContent, Long relatedContentId) {
        return builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .notificationContent(notificationContent)
                .relatedContentId(relatedContentId)
                .build();
    }

    public Notification toEntity() {
        return Notification.of(receiver, notificationType, notificationContent, relatedContentId);
    }

}