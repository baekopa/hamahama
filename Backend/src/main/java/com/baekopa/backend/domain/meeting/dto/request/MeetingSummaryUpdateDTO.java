package com.baekopa.backend.domain.meeting.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSummaryUpdateDTO {
    private String summaryText;

}
