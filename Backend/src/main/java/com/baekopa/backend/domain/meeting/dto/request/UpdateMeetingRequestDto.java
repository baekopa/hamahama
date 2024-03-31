package com.baekopa.backend.domain.meeting.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateMeetingRequestDto {

    private String topic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime studyAt;

}
