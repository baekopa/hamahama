package com.baekopa.backend.domain.meeting.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationRequestDto {

    private String originalText;

    @Builder
    private DeduplicationRequestDto(String originalText) {
        this.originalText = originalText;
    }

    public static DeduplicationRequestDto from(String originalText) {
        return builder().originalText(originalText).build();
    }

}
