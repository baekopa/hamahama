package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingResponseDTO {
    private Long meetingId; //미팅 id
    private String topic; //미팅 주제
    private String scriptContent; //미팅 스크립트(대화 원문)
    private String summaryContent; //미팅 요약문
    private String remindQuiz;
    private List<MeetingKeywordDTO> keyword;

    @Builder
    private MeetingResponseDTO(Long meetingId, String topic, String scriptContent, String summaryContent, String remindQuiz, List<MeetingKeywordDTO> keyword) {
        this.meetingId = meetingId;
        this.topic = topic;
        this.scriptContent = scriptContent;
        this.summaryContent = summaryContent;
        this.remindQuiz = remindQuiz;
        this.keyword = keyword;
    }

    public static MeetingResponseDTO of(Meeting meeting, MeetingScript meetingScript, MeetingSummary meetingSummary, RemindQuiz remindQuiz, List<MeetingKeywordDTO> keyword) {
        return builder()
                .meetingId(meeting.getId())
                .topic(meeting.getTopic())
                .scriptContent(meetingScript.getScriptContent())
                .summaryContent(meetingSummary.getSummaryContent())
                .remindQuiz(remindQuiz.getContent())
                .keyword(keyword)
                .build();
    }
}
