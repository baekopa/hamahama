package com.baekopa.backend.domain.meeting.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMeetingKeywordDTO {
    private Long groupKeywordId;
    private String keyword;//변경된 키워드

}
