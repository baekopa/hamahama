package com.baekopa.backend.domain.note.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoteSummaryRequestDto {

    private String originalText;

    @Builder
    private CreateNoteSummaryRequestDto(String originalText) {
        this.originalText = originalText;
    }

    public static CreateNoteSummaryRequestDto from(String originalText) {
        return builder().originalText(originalText).build();
    }

}
