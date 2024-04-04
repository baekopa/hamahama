package com.baekopa.backend.domain.meeting.dto.request;

import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMeetingRemindQuizDTO {
    private String summaryText;

    @Builder
    private CreateMeetingRemindQuizDTO(String summaryText) {
        this.summaryText = summaryText;
    }

    public static CreateMeetingRemindQuizDTO of(String summaryText){
        return builder()
                .summaryText(summaryText)
                .build();
    }

    public static CreateMeetingRemindQuizDTO from(MeetingSummary meetingSummary){
        return builder()
                .summaryText(meetingSummary.getSummaryContent())
                .build();
    }
}
