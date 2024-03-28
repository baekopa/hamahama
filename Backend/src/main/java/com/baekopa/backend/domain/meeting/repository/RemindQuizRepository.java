package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RemindQuizRepository extends JpaRepository<RemindQuiz, Long> {
    @Query("SELECT rq FROM RemindQuiz rq WHERE rq.meeting.id = :meetingId")
    Optional<RemindQuiz> findByIdAndDeletedAtIsNull(@Param("meetingId") Long meetingId);
}
