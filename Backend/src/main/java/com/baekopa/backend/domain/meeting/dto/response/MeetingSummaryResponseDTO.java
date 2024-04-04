package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSummaryResponseDTO {
    private String summaryText;

    @Builder
    private MeetingSummaryResponseDTO(String summaryText) {
        this.summaryText = summaryText;
    }

    public static MeetingSummaryResponseDTO from(MeetingSummaryDTO meetingSummaryDTO) {
        return builder()
                .summaryText(meetingSummaryDTO.getSummaryText())
                .build();
    }
    public static MeetingSummaryResponseDTO getMeetingSummary(MeetingSummary meetingSummary){
        return builder()
                .summaryText(meetingSummary.getSummaryContent())
                .build();
    }

}
