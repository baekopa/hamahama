package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.response.RemindQuizResponseDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.RemindQuizRepository;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemindQuizService {

    private final RemindQuizRepository remindQuizRepository;
    private final MeetingRepository meetingRepository;
    private final StudyRepository studyRepository;

    public List<RemindQuizResponseDto> getStudyRemindQuiz(Long studyId) {

        Study study = studyRepository.findByIdAndDeletedAtIsNull(studyId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));
        List<Meeting> meetingList = meetingRepository.findAllByStudyAndDeletedAtIsNullAndRecordFileIsNotNullOrderByStudyAtDesc(study);

        List<RemindQuizResponseDto> response = new ArrayList<>();

        for (Meeting meeting : meetingList) {

            log.warn("미팅 id : {}", meeting.getId());

            RemindQuiz remindQuiz = remindQuizRepository.findByMeetingAndDeletedAtIsNull(meeting)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));

            response.add(RemindQuizResponseDto.of(remindQuiz.getId(), meeting.getTopic(), meeting.getStudy().getTitle(),
                    meeting.getStudyAt(), remindQuiz.getOpenDate(),
                    LocalDateTime.now().isAfter(remindQuiz.getOpenDate()) || LocalDateTime.now().isEqual(remindQuiz.getOpenDate()),
                    remindQuiz.getModifiedAt()));

        }

        return response;
    }

    public RemindQuizResponseDto getRemindQuiz(Long studyId, Long remindQuizId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));

        RemindQuiz remindQuiz = remindQuizRepository.findById(remindQuizId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));

        Meeting meeting = remindQuiz.getMeeting();

        return RemindQuizResponseDto.of(study, meeting, remindQuiz);

    }

}
