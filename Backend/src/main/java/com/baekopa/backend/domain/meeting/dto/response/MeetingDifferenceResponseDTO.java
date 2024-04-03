package com.baekopa.backend.domain.meeting.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingDifferenceResponseDTO {
    private String difference;

    @Builder
    private MeetingDifferenceResponseDTO(String difference) {
        this.difference = difference;
    }

    public static MeetingDifferenceResponseDTO of(String difference) {
        return MeetingDifferenceResponseDTO.builder()
                .difference(difference)
                .build();
    }

}
