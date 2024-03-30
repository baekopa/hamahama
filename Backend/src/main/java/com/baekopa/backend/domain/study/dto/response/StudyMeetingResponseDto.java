package com.baekopa.backend.domain.study.dto.response;

import com.baekopa.backend.domain.note.dto.SubmittedNoteDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StudyMeetingResponseDto {

    private Long id; // meeting id
    private String topic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime studyAt;
    private List<SubmittedNoteDto> submittedNotes;
    private String entireSummary;

    @Builder
    private StudyMeetingResponseDto(Long id, String topic, LocalDateTime studyAt, List<SubmittedNoteDto> submittedNotes, String entireSummary) {
        this.id = id;
        this.topic = topic;
        this.studyAt = studyAt;
        this.submittedNotes = submittedNotes;
        this.entireSummary = entireSummary;
    }

    public static StudyMeetingResponseDto of(Long id, String topic, LocalDateTime studyAt, List<SubmittedNoteDto> submittedNotes, String entireSummary) {
        return builder().id(id)
                .topic(topic)
                .studyAt(studyAt)
                .submittedNotes(submittedNotes)
                .entireSummary(entireSummary)
                .build();
    }

}
