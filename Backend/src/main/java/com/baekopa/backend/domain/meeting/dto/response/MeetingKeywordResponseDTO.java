package com.baekopa.backend.domain.meeting.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingKeywordResponseDTO {
    private List<String> keyword;

    @Builder
    private MeetingKeywordResponseDTO(List<String> keyword) {
        this.keyword = keyword;
    }
}
