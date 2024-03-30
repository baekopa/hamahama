package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingListDto {

    private Long id; // meeting id
    private String topic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime studyAt;

    @Builder
    private MeetingListDto(Long id, String topic, LocalDateTime studyAt) {
        this.id = id;
        this.topic = topic;
        this.studyAt = studyAt;
    }

    public static MeetingListDto of(Long id, String topic, LocalDateTime studyAt) {
        return builder()
                .id(id)
                .topic(topic)
                .studyAt(studyAt)
                .build();
    }

    public static MeetingListDto from(Meeting meeting) {
        return builder()
                .id(meeting.getId())
                .topic(meeting.getTopic())
                .studyAt(meeting.getStudyAt())
                .build();
    }
}

