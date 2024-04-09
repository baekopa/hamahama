package com.baekopa.backend.practice;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.notification.repository.EmitterRepository;
import com.baekopa.backend.domain.notification.repository.NotificationRepository;
import com.baekopa.backend.domain.notification.service.EmitterService;
import com.baekopa.backend.domain.notification.service.NotificationService;
import com.baekopa.backend.global.oauth2.dto.OAuthProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RestApiTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private EmitterRepository emitterRepository;

    @Mock
    private NotificationRepository notificationRepository;
    @InjectMocks
    private EmitterService emitterService;

    @Mock
    private MemberRepository memberRepository;

    private Member sender;
    private Member receiver;

    @Test
    void 알림_구독() {
        // Given
        receiver = Member.of("이수민", "KAKAO 3410530476", "soomin0608@naver.com", "http://t1.kakaocdn.net/account_images/default_profile.jpeg.twg.thumb.R640x640", "ROLE_USER", OAuthProvider.KAKAO);

        // When
        SseEmitter emitter = emitterService.subscribe(receiver);

        // Then
        assertThat(emitter).isNotNull();
        verify(emitterRepository, times(1)).saveEmitter(anyString(), any(SseEmitter.class));
    }


    // 더미 이벤트 캐시
    private Map<String, SseEmitter> createDummyEvents() {
        Map<String, SseEmitter> events = new HashMap<>();
        events.put("id1", new SseEmitter());
        events.put("id2", new SseEmitter());

        return events;
    }
}
