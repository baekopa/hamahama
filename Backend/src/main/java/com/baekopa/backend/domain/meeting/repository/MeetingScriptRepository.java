package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingScriptRepository extends JpaRepository<MeetingScript, Long> {
}
