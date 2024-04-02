package com.baekopa.backend.domain.notification.entity;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE notification SET deleted_at = NOW() WHERE notification_id = ?")
public class Notification extends BaseBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @Column(name = "notification_content", nullable = false, length = 100)
    private String notificationContent;

    @Column(name = "is_check", nullable = false)
    private Boolean isChecked;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "related_content_id")
    private String relatedContentId;

    @Builder
    public Notification(Member receiver, NotificationType notificationType, String notificationContent, Boolean isChecked, String eventId, String relatedContentId) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
        this.isChecked = isChecked;
        this.eventId = eventId;
        this.relatedContentId = relatedContentId;
    }

    public static Notification of(Member receiver, NotificationType notificationType, String notificationContent, String eventId, String relatedContentId) {

        return builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .notificationContent(notificationContent)
                .isChecked(false)
                .eventId(eventId)
                .relatedContentId(relatedContentId)
                .build();
    }

    public void updateIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

}