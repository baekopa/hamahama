package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.MeetingKeyword;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeetingKeywordListDTO {
    private List<String> keyword;

    @Builder
    private MeetingKeywordListDTO(List<String> keyword) {
        this.keyword = keyword;
    }

    public static MeetingKeywordListDTO from(List<String> meetingKeywordList){
        return builder()
                .keyword(meetingKeywordList)
                .build();
    }

}
