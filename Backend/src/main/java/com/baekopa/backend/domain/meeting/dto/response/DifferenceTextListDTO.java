package com.baekopa.backend.domain.meeting.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DifferenceTextListDTO {
    private List<String> differenceTextList;

    @Builder
    private DifferenceTextListDTO(List<String> differenceTextList) {
        this.differenceTextList = differenceTextList;
    }

    public static DifferenceTextListDTO of(List<String> differenceTextList) {
        return builder().differenceTextList(differenceTextList).build();
    }
}
