package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MeetingScriptRepository extends JpaRepository<MeetingScript, Long> {

    Optional<MeetingScript> findByIdAndDeletedAtIsNull(Long aLong);
    Optional<MeetingScript> findByMeetingAndDeletedAtIsNull(Meeting meeting);
}
