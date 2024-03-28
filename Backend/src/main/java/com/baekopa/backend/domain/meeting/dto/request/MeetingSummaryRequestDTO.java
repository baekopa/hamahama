package com.baekopa.backend.domain.meeting.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingSummaryRequestDTO {
    private String originalText;

    @Builder
    public MeetingSummaryRequestDTO(String originalText) {
        this.originalText = originalText;
    }

    public static MeetingSummaryRequestDTO of(String originalText){
        return builder()
                .originalText(originalText)
                .build();
    }
}
