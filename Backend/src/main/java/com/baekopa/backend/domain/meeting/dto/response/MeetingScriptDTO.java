package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingScriptDTO {
    private Long meetingScriptId;
    private String scriptContent;

    @Builder
    private MeetingScriptDTO(Long meetingScriptId, String scriptContent) {
        this.meetingScriptId = meetingScriptId;
        this.scriptContent = scriptContent;
    }

    public static MeetingScriptDTO from(MeetingScript meetingScript){
        return builder()
                .meetingScriptId(meetingScript.getId())
                .scriptContent(meetingScript.getScriptContent())
                .build();
    }
}
