package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface RemindQuizRepository extends JpaRepository<RemindQuiz, Long> {

    Optional<RemindQuiz> findByIdAndDeletedAtIsNull(Long remindQuizId);

    Optional<RemindQuiz> findByMeetingAndDeletedAtIsNull(Meeting meeting);

    boolean existsByMeetingAndDeletedAtIsNull(Meeting meeting);

    @Query(value = "SELECT * FROM remind_quiz WHERE abs(TIMESTAMPDIFF(MINUTE, open_date, now())) <= 1 AND deleted_at is NULL AND open_date >= now() ", nativeQuery = true)
    List<RemindQuiz> findUpcomingRemindQuizzes();

    @Modifying
    @Query(value = "DELETE FROM remind_quiz WHERE MONTH(deleted_at)  <= MONTH(:thresholdDate)", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);

    @Modifying
    @Query(value = "DELETE FROM remind_quiz WHERE meeting_id = :meetingId", nativeQuery = true)
    int deleteByMeeting(Long meetingId);
}
