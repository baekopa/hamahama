package com.baekopa.backend.domain.meeting.repository;


import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface MeetingSummaryRepository extends JpaRepository<MeetingSummary, Long> {
    @Query("SELECT ms FROM MeetingSummary ms WHERE ms.meeting.id = :meetingId")
    Optional<MeetingSummary> findByIdAndDeletedAtIsNull(@Param("meetingId") Long meetingId);

    boolean existsByMeetingAndDeletedAtIsNull(Meeting meeting);

    @Modifying
    @Query(value = "DELETE FROM meeting_summary WHERE MONTH(deleted_at)  <= MONTH(:thresholdDate)", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);

    @Modifying
    @Query(value = "DELETE FROM meeting_summary WHERE meeting_id = :meetingId", nativeQuery = true)
    int deleteByMeeting(Long meetingId);
}
