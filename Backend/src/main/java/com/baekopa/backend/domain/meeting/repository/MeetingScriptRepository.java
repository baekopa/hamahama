package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;

public interface MeetingScriptRepository extends JpaRepository<MeetingScript, Long> {

    Optional<MeetingScript> findByIdAndDeletedAtIsNull(Long aLong);

    Optional<MeetingScript> findByMeetingAndDeletedAtIsNull(Meeting meeting);

    @Modifying
    @Query(value = "DELETE FROM meeting_script WHERE MONTH(deleted_at)  <= MONTH(:thresholdDate)", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);

    @Modifying
    @Query(value = "DELETE FROM meeting_script WHERE meeting_id = :meetingId", nativeQuery = true)
    int deleteByMeeting(Long meetingId);
}
