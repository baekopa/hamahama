package com.baekopa.backend.domain.meeting.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingScriptRequestDto {

    private List<Transcription> transcriptions;

    public static class Transcription {
        private String speaker;
        private String text;

        // Getter and Setter for speaker and text
        public String getSpeaker() {
            return speaker;
        }

        public void setSpeaker(String speaker) {
            this.speaker = speaker;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "Speaker: " + speaker + ", Text: " + text;
        }
    }
}
