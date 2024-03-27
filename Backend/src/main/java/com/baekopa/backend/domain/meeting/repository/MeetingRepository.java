package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findAllByStudyAndDeletedAtIsNull(Study study);

    Optional<Meeting> findById(Long meetingId);

    Optional<Meeting> findTopByStudyAndDeletedAtIsNullAndStudyAtGreaterThanEqualOrderByStudyAtAsc(Study study, LocalDateTime currentDate);

    Optional<Meeting> findTopByStudyAndDeletedAtIsNullAndStudyAtLessThanEqualOrderByStudyAtDesc(Study study, LocalDateTime currentDate);

}
