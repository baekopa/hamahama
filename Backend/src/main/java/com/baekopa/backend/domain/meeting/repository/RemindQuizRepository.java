package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import com.baekopa.backend.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RemindQuizRepository extends JpaRepository<RemindQuiz, Long> {
    Optional<RemindQuiz> findByMeetingAndDeletedAtIsNull(Meeting meeting);

    List<RemindQuiz> findByStudyAndDeletedAtIsNull(Study study);

}
