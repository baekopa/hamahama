package com.baekopa.backend.domain.meeting.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyMeetingListDto {

    private Long id;
    private String studyName;
    private String studyDay;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<MeetingListDto> meetings;

    @Builder
    private StudyMeetingListDto(Long id, String studyName, String studyDay, LocalDate startDate, LocalDate endDate,
                                List<MeetingListDto> meetings) {
        this.id = id;
        this.studyName = studyName;
        this.studyDay = studyDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.meetings = meetings;
    }

    public static StudyMeetingListDto of(String studyName, List<MeetingListDto> meetings) {
        return builder().studyName(studyName).meetings(meetings).build();
    }

    public static StudyMeetingListDto of(Long id, String studyName, String studyDay, LocalDate startDate, LocalDate endDate,
                                         List<MeetingListDto> meetings) {
        return builder()
                .id(id)
                .studyName(studyName)
                .studyDay(studyDay)
                .startDate(startDate)
                .endDate(endDate)
                .meetings(meetings)
                .build();
    }

}
