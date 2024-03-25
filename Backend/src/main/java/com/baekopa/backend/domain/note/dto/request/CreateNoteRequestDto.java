package com.baekopa.backend.domain.note.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoteRequestDto {

    private String title;

    private String content;

    private Long writer;

}
