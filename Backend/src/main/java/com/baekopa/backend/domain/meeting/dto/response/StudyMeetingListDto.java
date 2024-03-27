package com.baekopa.backend.domain.meeting.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudyMeetingListDto {

    private String studyName;
    private List<MeetingListDto> meetings;

    @Builder
    private StudyMeetingListDto(String studyName, List<MeetingListDto> meetings) {
        this.studyName = studyName;
        this.meetings = meetings;
    }

    public static StudyMeetingListDto of(String studyName, List<MeetingListDto> meetings) {
        return builder().studyName(studyName).meetings(meetings).build();
    }

}
