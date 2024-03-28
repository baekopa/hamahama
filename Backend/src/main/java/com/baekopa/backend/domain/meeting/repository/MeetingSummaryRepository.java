package com.baekopa.backend.domain.meeting.repository;


import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingSummaryRepository extends JpaRepository<MeetingSummary, Long> {
    Optional<MeetingSummary> findByIdAndDeletedAtIsNull(Long id);
}
