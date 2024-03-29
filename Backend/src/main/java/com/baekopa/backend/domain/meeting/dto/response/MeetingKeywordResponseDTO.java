package com.baekopa.backend.domain.meeting.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MeetingKeywordResponseDTO {
    private List<String> keyword;

    @Builder
    private MeetingKeywordResponseDTO(List<String> keyword) {
        this.keyword = keyword;
    }
}
