package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.MeetingKeyword;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingKeywordDTO {
    private Long keywordId;
    private String keyword;

    @Builder
    private MeetingKeywordDTO(Long keywordId, String keyword) {
        this.keywordId = keywordId;
        this.keyword = keyword;
    }

    public static MeetingKeywordDTO from(MeetingKeyword meetingKeyword) {
        return builder()
                .keywordId(meetingKeyword.getId())
                .keyword(meetingKeyword.getKeyword())
                .build();
    }
}
