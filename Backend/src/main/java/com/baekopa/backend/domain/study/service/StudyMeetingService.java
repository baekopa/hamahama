package com.baekopa.backend.domain.study.service;

import com.baekopa.backend.domain.meeting.dto.request.CreateMeetingRequestDto;
import com.baekopa.backend.domain.meeting.dto.request.UpdateMeetingRequestDto;
import com.baekopa.backend.domain.meeting.dto.response.CreateMeetingResponseDto;
import com.baekopa.backend.domain.meeting.dto.response.MeetingListDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.note.dto.SubmittedNoteDto;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.baekopa.backend.domain.study.dto.response.StudyMeetingResponseDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyMeetingService {

    private final MeetingRepository meetingRepository;
    private final StudyRepository studyRepository;
    private final SubmittedNoteRepository submittedNoteRepository;

    // 스터디 미팅 생성
    @Transactional
    public CreateMeetingResponseDto createNewMeeting(Long studyId, CreateMeetingRequestDto requestDto) {

        Study study = studyRepository.findByIdAndDeletedAtIsNull(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));

        Meeting meeting = meetingRepository.save(Meeting.of(requestDto.getTopic(), requestDto.getStudyAt(), study));

        return CreateMeetingResponseDto.of(meeting.getId(), meeting.getTopic(), meeting.getStudyAt());

    }

    // 스터디 미팅 목록 조회
    public List<MeetingListDto> getScheduledMeeting(Long studyId) {

        Study study = studyRepository.findByIdAndDeletedAtIsNull(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));

        LocalDateTime current = LocalDateTime.now();

        return meetingRepository.findAllByStudyAndDeletedAtIsNullAndStudyAtGreaterThanEqualOrderByStudyAtAsc(study, current)
                .stream().map(this::convertToDto).toList();

    }

    public MeetingListDto convertToDto(Meeting meeting) {
        return MeetingListDto.of(meeting.getId(), meeting.getTopic(), meeting.getStudyAt());
    }


    // 스터디 예정 일정 조회 , 없다면 null 반환
    public StudyMeetingResponseDto getStudyMeeting(Long studyId) {

        Study study = studyRepository.findByIdAndDeletedAtIsNull(studyId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));
        Meeting meeting = meetingRepository.findTopByStudyAndDeletedAtIsNullAndStudyAtGreaterThanEqualOrderByStudyAtAsc(study, LocalDateTime.now()).orElse(null);

        if (meeting == null) {
            return null;
        }

        List<SubmittedNoteDto> submittedNoteDtoList = submittedNoteRepository.findAllByMeetingAndDeletedAtIsNull(meeting)
                .stream().map(SubmittedNoteDto::of).toList();

        return StudyMeetingResponseDto.of(meeting.getId(),
                meeting.getTopic(),
                meeting.getStudyAt(),
                submittedNoteDtoList,
                meeting.getNoteSummary());
    }

    @Transactional
    public List<MeetingListDto> updateMeeting(Long studyId, Long meetingId, UpdateMeetingRequestDto requestDto) {

        Meeting meeting = meetingRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));

        // 수정
        if (requestDto.getTopic() != null) {
            meeting.updateTopic(requestDto.getTopic());
        }

        if (requestDto.getStudyAt() != null) {
            meeting.updateStudyAt(requestDto.getStudyAt());
        }

        return getScheduledMeeting(studyId);

    }
}
