package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.MeetingKeyword;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingKeywordListDTO {
    private List<MeetingKeywordDTO> keyword;

    @Builder
    private MeetingKeywordListDTO(List<MeetingKeywordDTO> keyword) {
        this.keyword = keyword;
    }


    public static MeetingKeywordListDTO from(List<MeetingKeywordDTO> meetingKeywordList){
        return builder()
                .keyword(meetingKeywordList)
                .build();
    }



}
