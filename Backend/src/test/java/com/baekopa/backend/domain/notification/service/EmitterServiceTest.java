package com.baekopa.backend.domain.notification.service;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.entity.NotificationType;
import com.baekopa.backend.domain.notification.repository.EmitterRepository;
import com.baekopa.backend.domain.notification.repository.NotificationRepository;
import com.baekopa.backend.global.oauth2.dto.OAuthProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmitterServiceTest {

    @Mock
    private EmitterRepository emitterRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private EmitterService emitterService;
    private Member receiver;

    @Test
    void 알림_구독() {

        // Given
        receiver = Member.of("받는사람", "KAKAO 1234567890", "receiver@google.com", "image2", "ROLE_USER", OAuthProvider.GOOGLE);

        // When
        SseEmitter emitter = emitterService.subscribe(receiver);

        // Then
        assertThat(emitter).isNotNull();
        verify(emitterRepository, times(1)).saveEmitter(anyString(), any(SseEmitter.class));
    }

    @Test
    void 알림_전송() {
        // Given
        NotificationType notificationType = NotificationType.INVITE;
        String notificationContent = "알림 내용";
        String relatedContentId = "연관된 객체 아이디";

        //// When
        emitterService.send(receiver, notificationType, notificationContent, relatedContentId);

        //// Then
        verify(notificationRepository, times(1)).save(any());
        verify(emitterRepository, times(1)).saveEvent(any(), any());
        verify(emitterRepository, times(1)).findAllEmitterStartWithKey(any());
    }
}