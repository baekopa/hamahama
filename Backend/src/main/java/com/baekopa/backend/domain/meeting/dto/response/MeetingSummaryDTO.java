package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSummaryDTO {
    private String originalText;
    private String summaryText;

//    @Builder
//    private MeetingSummaryDTO(String originalText, String summaryText) {
//        this.originalText = originalText;
//        this.summaryText = summaryText;
//    }
//
//    public static MeetingSummaryDTO from(MeetingSummary meetingSummary){
//        return builder()
//                .summaryText(meetingSummary.getSummaryContent())
//                .build();
//    }
}
