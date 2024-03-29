package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingKeywordRepository extends JpaRepository<MeetingKeyword, Long> {
    boolean existsByMeetingAndDeletedAtIsNull(Meeting meeting);
    Optional<MeetingKeyword> findByIdAndDeletedAtIsNull(Long id);

    List<MeetingKeyword> findAllByMeetingAndDeletedAtIsNull(Meeting meeting);
}
