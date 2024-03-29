package com.baekopa.backend.domain.meeting.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
