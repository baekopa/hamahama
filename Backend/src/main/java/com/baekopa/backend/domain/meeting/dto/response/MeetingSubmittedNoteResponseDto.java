package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.note.dto.SubmittedNoteDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeetingSubmittedNoteResponseDto {

    private List<SubmittedNoteDto> submittedNotes;
    private String entireSummary;

    @Builder
    private MeetingSubmittedNoteResponseDto(List<SubmittedNoteDto> submittedNotes, String entireSummary) {
        this.submittedNotes = submittedNotes;
        this.entireSummary = entireSummary;

    }

    public static MeetingSubmittedNoteResponseDto of(List<SubmittedNoteDto> submittedNotes, String entireSummary) {
        return builder().submittedNotes(submittedNotes)
                .entireSummary(entireSummary)
                .build();
    }

}
