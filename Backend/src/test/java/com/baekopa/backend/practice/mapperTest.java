package com.baekopa.backend.practice;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.dto.response.NotificationResponseDto;
import com.baekopa.backend.domain.notification.entity.Notification;
import com.baekopa.backend.domain.notification.entity.NotificationType;
import com.baekopa.backend.domain.notification.mapper.NotificationMapper;
import com.baekopa.backend.global.oauth2.dto.OAuthProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class mapperTest {

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private ModelMapper modelMapper;

    private Notification notification;
    private long startTime;
    private long endTime;


    @BeforeEach
    void setUp() {
        Member sender = Member.of("보낸사람", "KAKAO 0123456789", "sender@naver.com", "image1", "ROLE_USER", OAuthProvider.KAKAO);

        Member receiver = Member.of("받는사람", "KAKAO 1234567890", "receiver@google.com", "image2", "ROLE_USER", OAuthProvider.GOOGLE);

        NotificationType notificationType = NotificationType.INVITE;
        String notificationContent = "알림 내용";
        String relatedContentId = "연관된 객체 아이디";
        String key = "key";
        String eventId = "eventId";

        notification = Notification.of(receiver, notificationType, notificationContent, eventId, relatedContentId);
    }

    @Test
    public void of_test() throws Exception {
        //given
        startTime = System.nanoTime();

        //when
        NotificationResponseDto responseDto = NotificationResponseDto.of(notification);

        //then
        endTime = System.nanoTime();
        printExecutionTime("of_test");
    }

    @Test
    public void objectMapper_test() throws Exception {
        //given
        startTime = System.nanoTime();

        //when
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NotificationResponseDto responseDto = objectMapper.convertValue(notification, NotificationResponseDto.class);

        //then
        endTime = System.nanoTime();
        printExecutionTime("objectMapper");
    }

    @Test
    public void modelMapper_test() throws Exception {
        //given
        startTime = System.nanoTime();

        //when
        NotificationResponseDto responseDto = modelMapper.map(notification, NotificationResponseDto.class);

        //then
        endTime = System.nanoTime();
        printExecutionTime("modelMapper");
    }

    @Test
    public void mapstruct_test() throws Exception {
        //given
        startTime = System.nanoTime();

        //when
        NotificationResponseDto responseDto = notificationMapper.notificationToNotificationResponseDto(notification);

        //then
        endTime = System.nanoTime();
        printExecutionTime("mapstruct");
    }

    private void printExecutionTime(String methodName) {
        long duration = (endTime - startTime) / 1000000; // milliseconds
        System.out.println(methodName + " : " + duration + " milliseconds");
    }
}
