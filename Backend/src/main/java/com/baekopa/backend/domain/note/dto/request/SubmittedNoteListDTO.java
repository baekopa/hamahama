package com.baekopa.backend.domain.note.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmittedNoteListDTO {
    private List<String> submittedNoteList;

    @Builder
    private SubmittedNoteListDTO(List<String> submittedNoteList) {
        this.submittedNoteList = submittedNoteList;
    }

    public static SubmittedNoteListDTO of(List<String> submittedNoteList) {
        return builder().submittedNoteList(submittedNoteList).build();
    }
}
