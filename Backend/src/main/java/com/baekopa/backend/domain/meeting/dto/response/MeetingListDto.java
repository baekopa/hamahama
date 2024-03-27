package com.baekopa.backend.domain.meeting.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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

}

