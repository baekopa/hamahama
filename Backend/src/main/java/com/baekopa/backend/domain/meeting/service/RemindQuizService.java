package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.repository.RemindQuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemindQuizService {

    private final RemindQuizRepository remindQuizRepository;

    public void getStudyRemindQuiz(Long StudyId) {

    }

}
