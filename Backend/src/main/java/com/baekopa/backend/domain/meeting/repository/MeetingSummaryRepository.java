package com.baekopa.backend.domain.meeting.repository;


import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MeetingSummaryRepository extends JpaRepository<MeetingSummary, Long> {
    @Query("SELECT ms FROM MeetingSummary ms WHERE ms.meeting.id = :meetingId")
    Optional<MeetingSummary> findByIdAndDeletedAtIsNull(@Param("meetingId") Long meetingId);

}
