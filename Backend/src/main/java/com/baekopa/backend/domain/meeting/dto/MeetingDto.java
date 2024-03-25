package com.baekopa.backend.domain.meeting.dto;

import lombok.Builder;

import java.time.LocalDate;

public class MeetingDto {

    private Long id;

    private String topic;

    private LocalDate studyAt;

    private Long studyId;

    private String studyName;

    @Builder
    private MeetingDto(Long id, String topic, LocalDate studyAt, Long studyId, String studyName) {
        this.id = id;
        this.topic = topic;
        this.studyAt = studyAt;
        this.studyId = studyId;
        this.studyName = studyName;
    }

    public static MeetingDto of(Long id, String topic, LocalDate studyAt, Long studyId, String studyName) {
        return builder().id(id)
                .topic(topic)
                .studyAt(studyAt)
                .studyId(studyId)
                .studyName(studyName)
                .build();
    }
}
