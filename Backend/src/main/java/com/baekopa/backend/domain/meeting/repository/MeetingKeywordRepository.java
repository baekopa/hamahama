package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface MeetingKeywordRepository extends JpaRepository<MeetingKeyword, Long> {
    boolean existsByMeetingAndDeletedAtIsNull(Meeting meeting);

    Optional<MeetingKeyword> findByIdAndDeletedAtIsNull(Long id);

    List<MeetingKeyword> findAllByMeetingAndDeletedAtIsNull(Meeting meeting);

    @Modifying
    @Query(value = "DELETE FROM meeting_keyword WHERE MONTH(deleted_at) = :thresholdDate", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);
}
