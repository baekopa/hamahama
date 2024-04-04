package com.baekopa.backend.domain.meeting.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMeetingKeywordDTO {
    private Long groupKeywordId;
    private String keyword;//변경된 키워드

}
