package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingStudyDTO {
    private Long id;//meeting id
    private String topic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime studyAt;
    private List<MeetingKeywordDTO> keyword;
    private List<MeetingMemberInfoDTO> memberInfoDTOList;

    @Builder
    private MeetingStudyDTO(Long id, String topic, LocalDateTime studyAt, List<MeetingKeywordDTO> keyword, List<MeetingMemberInfoDTO> memberInfoDTOList) {
        this.id = id;
        this.topic = topic;
        this.studyAt = studyAt;
        this.keyword = keyword;
        this.memberInfoDTOList = memberInfoDTOList;
    }

    public static MeetingStudyDTO of(Meeting meeting, List<MeetingKeywordDTO> meetingKeywordDTOList, List<MeetingMemberInfoDTO> memberInfoDTOList){
        return builder()
                .id(meeting.getId())
                .topic(meeting.getTopic())
                .studyAt(meeting.getStudyAt())
                .keyword(meetingKeywordDTOList)
                .memberInfoDTOList(memberInfoDTOList)
                .build();
    }
}
