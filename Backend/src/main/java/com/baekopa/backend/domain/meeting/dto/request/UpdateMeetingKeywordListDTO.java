package com.baekopa.backend.domain.meeting.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMeetingKeywordListDTO {
    private List<UpdateMeetingKeywordDTO> updateMeetingKeywordList;
}
