package com.baekopa.backend.domain.note.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoteSummaryRequestDto {

    private String meetingText;

    @Builder
    private CreateNoteSummaryRequestDto(String meetingText) {
        this.meetingText = meetingText;
    }

    public static CreateNoteSummaryRequestDto from(String originalText) {
        return builder().meetingText(originalText).build();
    }

}
