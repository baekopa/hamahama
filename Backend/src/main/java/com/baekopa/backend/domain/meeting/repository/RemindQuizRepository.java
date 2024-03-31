package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RemindQuizRepository extends JpaRepository<RemindQuiz, Long> {
    Optional<RemindQuiz> findByMeetingAndDeletedAtIsNull(Meeting meeting);

    Optional<RemindQuiz> findByIdAndDeletedAtIsNull(Long remindQuizId);

}
