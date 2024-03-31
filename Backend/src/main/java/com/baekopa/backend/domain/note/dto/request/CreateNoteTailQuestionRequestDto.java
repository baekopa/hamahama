package com.baekopa.backend.domain.note.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoteTailQuestionRequestDto {

    private String originalText;

    @Builder
    private CreateNoteTailQuestionRequestDto(String originalText) {
        this.originalText = originalText;
    }

    public static CreateNoteTailQuestionRequestDto from(String originalText) {
        return builder().originalText(originalText).build();
    }

}
