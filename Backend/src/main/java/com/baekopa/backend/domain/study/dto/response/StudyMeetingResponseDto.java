package com.baekopa.backend.domain.study.dto.response;

import com.baekopa.backend.domain.meeting.entity.Meeting;
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

    public static StudyMeetingResponseDto of(Meeting meeting, List<SubmittedNoteDto> submittedNotes) {
        return builder().id(meeting.getId())
                .topic(meeting.getTopic())
                .studyAt(meeting.getStudyAt())
                .submittedNotes(submittedNotes)
                .entireSummary(meeting.getNoteSummary())
                .build();
    }

}
