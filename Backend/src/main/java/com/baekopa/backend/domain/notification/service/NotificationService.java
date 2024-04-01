package com.baekopa.backend.domain.notification.service;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.dto.response.NotificationListResponseDto;
import com.baekopa.backend.domain.notification.dto.response.NotificationResponseDto;
import com.baekopa.backend.domain.notification.entity.Notification;
import com.baekopa.backend.domain.notification.repository.EmitterRepository;
import com.baekopa.backend.domain.notification.repository.NotificationRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    // 미확인 알림 목록 조회
    @Transactional
    public NotificationListResponseDto getNotificationList(Member member) {

        return NotificationListResponseDto.from(
                notificationRepository.findAllByReceiverAndIsCheckedIsFalseAndDeletedAtIsNullOrderByCreatedAtDesc(member).stream()
                        .map(NotificationResponseDto::of).toList(),
                member.getLastNotificationEventId()
        );
    }

    // 미확인 알림 목록 확인 시간 갱신
    @Transactional
    public void updateLastEventId(Member member, String lastEventId) {

        member.updateLastNotificationEventId(lastEventId);
    }

    // 특정 알림 확인
    @Transactional
    public void readNotification(Member member, Long notificationId) {

        Notification notification = notificationRepository.findByIdAndReceiverAndDeletedAtIsNull(notificationId, member)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND, ErrorCode.NOTIFICATION_NOT_FOUND.getMessage()));

        notification.updateIsChecked(true);

        String eventId = notification.getEventId();
        emitterRepository.deleteEventById(eventId);
    }

}
