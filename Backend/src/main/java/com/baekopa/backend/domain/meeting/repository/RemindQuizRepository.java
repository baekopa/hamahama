package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RemindQuizRepository extends JpaRepository<RemindQuiz, Long> {
    Optional<RemindQuiz> findByMeetingAndDeletedAtIsNull(Meeting meeting);

    Optional<RemindQuiz> findByIdAndDeletedAtIsNull(Long remindQuizId);

    @Query(value = "SELECT * FROM remind_quiz WHERE abs(TIMESTAMPDIFF(MINUTE, open_date, now())) <= 1 AND deleted_at is NULL AND open_date >= now() ", nativeQuery = true)
    List<RemindQuiz> findUpcomingRemindQuizzes();

}
