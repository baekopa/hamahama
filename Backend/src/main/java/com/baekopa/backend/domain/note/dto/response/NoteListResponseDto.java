package com.baekopa.backend.domain.note.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NoteListResponseDto {

    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt;

    private List<SubmittedStudyDto> studies;

    @Builder
    private NoteListResponseDto(Long id, String title, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public static NoteListResponseDto of(Long id, String title, LocalDateTime createAt, LocalDateTime modifiedAt) {
        return builder().id(id).title(title).createAt(createAt).modifiedAt(modifiedAt).build();
    }

}
