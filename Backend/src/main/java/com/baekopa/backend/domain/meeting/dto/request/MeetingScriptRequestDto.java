package com.baekopa.backend.domain.meeting.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeetingScriptRequestDto {

    private List<Transcription> transcriptions;
    public static class Transcription {
        private String speaker;
        private String text;
    }
}
