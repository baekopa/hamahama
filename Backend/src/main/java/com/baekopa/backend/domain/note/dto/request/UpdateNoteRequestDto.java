package com.baekopa.backend.domain.note.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNoteRequestDto {

    private String title;
    private String content;

}
