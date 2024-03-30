package com.baekopa.backend.domain.member.dto.response;

import com.baekopa.backend.domain.meeting.dto.response.StudyMeetingListDto;
import com.baekopa.backend.domain.notification.dto.response.NotificationResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyDashboardResponseDto {

    private List<NotificationResponseDto> notifications; // 알림 목록

    private List<StudyMeetingListDto> weekStudies;

    @Builder
    private MyDashboardResponseDto(List<NotificationResponseDto> notifications, List<StudyMeetingListDto> weekStudies) {
        this.notifications = notifications;
        this.weekStudies = weekStudies;
    }

    public static MyDashboardResponseDto of(List<NotificationResponseDto> notifications, List<StudyMeetingListDto> weekStudies) {
        return builder()
                .notifications(notifications)
                .weekStudies(weekStudies)
                .build();
    }

}
