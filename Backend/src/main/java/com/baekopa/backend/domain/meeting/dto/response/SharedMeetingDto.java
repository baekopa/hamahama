package com.baekopa.backend.domain.meeting.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SharedMeetingDto {

    private Long id;

    private String topic;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime studyAt;

    private Long studyId;

    private String studyName;

    private String studyImage;

    @Builder
    private SharedMeetingDto(Long id, String topic, LocalDateTime studyAt, Long studyId, String studyName, String studyImage) {
        this.id = id;
        this.topic = topic;
        this.studyAt = studyAt;
        this.studyId = studyId;
        this.studyName = studyName;
        this.studyImage = studyImage;
    }

    public static SharedMeetingDto of(Long id, String topic, LocalDateTime studyAt, Long studyId, String studyName, String studyImage) {
        return builder().id(id)
                .topic(topic)
                .studyAt(studyAt)
                .studyId(studyId)
                .studyName(studyName)
                .studyImage(studyImage)
                .build();
    }
}
