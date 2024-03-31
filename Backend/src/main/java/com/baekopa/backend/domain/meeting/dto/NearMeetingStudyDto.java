package com.baekopa.backend.domain.meeting.dto;

import java.time.LocalDateTime;

public interface NearMeetingStudyDto {

    Long getId();
    String getTitle();
    String getBackgroundImage();
    String getCategory();

    LocalDateTime getFutureMeeting();
    LocalDateTime getPastMeeting();


}
