package com.baekopa.backend.domain.note.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubmittedNoteSummaryRequestDto {

    private String originText;

    @Builder
    private CreateSubmittedNoteSummaryRequestDto (String originText) {
        this.originText = originText;
    }

    public static CreateSubmittedNoteSummaryRequestDto from(String originText){
        return builder().originText(originText).build();
    }
}
