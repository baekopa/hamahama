package com.baekopa.backend.domain.note.dto.response;

import com.baekopa.backend.domain.note.entity.Note;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoteListResponseDto {

    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt;

    private Boolean isShared;

    @Builder
    private NoteListResponseDto(Long id, String title, LocalDateTime createAt, LocalDateTime modifiedAt, Boolean isShared) {
        this.id = id;
        this.title = title;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
        this.isShared = isShared;
    }

    public static NoteListResponseDto of(Long id, String title, LocalDateTime createAt, LocalDateTime modifiedAt) {
        return builder().id(id).title(title).createAt(createAt).modifiedAt(modifiedAt).build();
    }

    public static NoteListResponseDto of(Note note, Boolean isShared) {
        return builder()
                .id(note.getId())
                .title(note.getTitle())
                .createAt(note.getCreatedAt())
                .modifiedAt(note.getModifiedAt())
                .isShared(isShared)
                .build();
    }

}
