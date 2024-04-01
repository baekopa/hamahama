package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.study.entity.Study;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InStudyMeetingListDTO {
    private Long id;
    private String studyName;
    private String studyDay;
    private List<MeetingStudyDTO> meetingStudyDTOList;

    @Builder
    private InStudyMeetingListDTO(Long id, String studyName, String studyDay, List<MeetingStudyDTO> meetingStudyDTOList) {
        this.id = id;
        this.studyName = studyName;
        this.studyDay = studyDay;
        this.meetingStudyDTOList = meetingStudyDTOList;
    }

    public static InStudyMeetingListDTO of(Study study, List<MeetingStudyDTO> meetingStudyDTOList){
        return builder()
                .id(study.getId())
                .studyName(study.getTitle())
                .studyDay(study.getDay())
                .meetingStudyDTOList(meetingStudyDTOList)
                .build();
    }
}
