package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import com.baekopa.backend.domain.study.entity.Study;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class RemindQuizResponseDto {

    private Long remindQuizId;
    private String topic; // meeting topic
    private String studyName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime studyAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime openAt;
    private boolean isOpened;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastModifiedAt;
    private String content;

    @Builder
    private RemindQuizResponseDto(Long remindQuizId, String topic, String studyName, LocalDateTime studyAt,
                                  LocalDateTime openAt, boolean isOpened, LocalDateTime lastModifiedAt, String content) {

        this.remindQuizId = remindQuizId;
        this.topic = topic;
        this.studyAt = studyAt;
        this.studyName = studyName;
        this.openAt = openAt;
        this.isOpened = isOpened;
        this.lastModifiedAt = lastModifiedAt;
        this.content = content;

    }

    public static RemindQuizResponseDto of(Study study, Meeting meeting, RemindQuiz remindQuiz) {

        return builder().remindQuizId(remindQuiz.getId())
                .topic(meeting.getTopic())
                .studyName(study.getTitle())
                .studyAt(meeting.getStudyAt())
                .openAt(remindQuiz.getOpenDate())
                .isOpened(remindQuiz.getOpenDate().compareTo(LocalDateTime.now()) < 0 ? true : false)
                .lastModifiedAt(remindQuiz.getModifiedAt())
                .content(remindQuiz.getContent())
                .build();
    }
}
