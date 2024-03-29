package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
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
    private List<MeetingKeywordDTO> keyword;
    private List<MeetingMemberInfoDTO> memberInfoList;

    @Builder
    private MeetingResponseDTO(Long meetingId, String topic, String scriptContent, String summaryContent, List<MeetingKeywordDTO> keyword, List<MeetingMemberInfoDTO> memberInfoList) {
        this.meetingId = meetingId;
        this.topic = topic;
        this.scriptContent = scriptContent;
        this.summaryContent = summaryContent;
        this.keyword = keyword;
        this.memberInfoList = memberInfoList;
    }


    public static MeetingResponseDTO of(Meeting meeting, MeetingScript meetingScript, MeetingSummary meetingSummary, List<MeetingKeywordDTO> keyword, List<MeetingMemberInfoDTO> meetingMemberInfoDTOList) {
        return builder()
                .meetingId(meeting.getId())
                .topic(meeting.getTopic())
                .scriptContent(meetingScript.getScriptContent())
                .summaryContent(meetingSummary.getSummaryContent())
                .keyword(keyword)
                .memberInfoList(meetingMemberInfoDTOList)
                .build();
    }
}
