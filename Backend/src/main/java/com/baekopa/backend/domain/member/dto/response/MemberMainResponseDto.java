package com.baekopa.backend.domain.member.dto.response;

import com.baekopa.backend.domain.note.dto.response.NoteListResponseDto;
import com.baekopa.backend.domain.study.dto.response.StudyListResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberMainResponseDto {

    StudyListResponseDto personalStudy;
    List<NoteListResponseDto> notes;
    List<StudyListResponseDto> studies;

    @Builder
    private MemberMainResponseDto(StudyListResponseDto personalStudy, List<NoteListResponseDto> notes, List<StudyListResponseDto> studies) {
        this.personalStudy = personalStudy;
        this.notes = notes;
        this.studies = studies;
    }

    public static MemberMainResponseDto of(StudyListResponseDto personalStudy, List<NoteListResponseDto> notes, List<StudyListResponseDto> studies) {
        return builder()
                .personalStudy(personalStudy)
                .notes(notes)
                .studies(studies)
                .build();
    }
}
