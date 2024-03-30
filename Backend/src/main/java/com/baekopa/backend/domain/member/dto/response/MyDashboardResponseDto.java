package com.baekopa.backend.domain.member.dto.response;

import com.baekopa.backend.domain.meeting.dto.response.MeetingResponseDTO;
import com.baekopa.backend.domain.notification.dto.response.NotificationListResponseDto;
import com.baekopa.backend.domain.notification.dto.response.NotificationResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MyDashboardResponseDto {

    List<NotificationResponseDto> notifications; // 알림 목록

    List<WeekStudyResponseDto> weekStudies;

    List<MeetingResponseDTO> meetings; // 예정된 미팅 일정 조회
}
