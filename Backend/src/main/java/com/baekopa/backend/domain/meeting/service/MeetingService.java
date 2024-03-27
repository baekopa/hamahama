package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.request.CreateMeetingRequestDto;
import com.baekopa.backend.domain.meeting.dto.response.CreateMeetingResponseDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final StudyRepository studyRepository;

    public CreateMeetingResponseDto createNewMeeting(CreateMeetingRequestDto requestDto) {

        Study study = studyRepository.findByIdAndDeletedAtIsNull(requestDto.getStudyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));

        Meeting meeting = meetingRepository.save(Meeting.of(requestDto.getTopic(), requestDto.getStudyAt(), study));

        return CreateMeetingResponseDto.of(meeting.getId(), meeting.getTopic(), meeting.getStudyAt());

    }

}
