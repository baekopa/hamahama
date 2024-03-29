package com.baekopa.backend.domain.meeting.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateMeetingResponseDto {

    private Long id;
    private String topic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime studyAt;

    @Builder
    private CreateMeetingResponseDto(Long id, String topic, LocalDateTime studyAt) {
        this.id = id;
        this.topic = topic;
        this.studyAt = studyAt;
    }

    public static CreateMeetingResponseDto of(Long id, String topic, LocalDateTime studyAt) {
        return builder()
                .id(id)
                .topic(topic)
                .studyAt(studyAt)
                .build();
    }

}
