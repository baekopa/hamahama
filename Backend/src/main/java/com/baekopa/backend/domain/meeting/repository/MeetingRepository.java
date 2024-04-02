package com.baekopa.backend.domain.meeting.repository;

import com.baekopa.backend.domain.meeting.dto.NearMeetingStudyDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.study.entity.Study;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findAllByStudyAndDeletedAtIsNull(Study study);

    Optional<Meeting> findByIdAndDeletedAtIsNull(Long meetingId);

    List<Meeting> findAllByStudyAndDeletedAtIsNullAndStudyAtGreaterThanEqualOrderByStudyAtAsc(Study study, LocalDateTime current);

    Optional<Meeting> findTopByStudyAndDeletedAtIsNullAndStudyAtGreaterThanEqualOrderByStudyAtAsc(Study study, LocalDateTime currentDate);

    Optional<Meeting> findTopByStudyAndDeletedAtIsNullAndStudyAtLessThanEqualOrderByStudyAtDesc(Study study, LocalDateTime currentDate);

    @Query(value = "SELECT * FROM Meeting WHERE abs(TIMESTAMPDIFF(MINUTE, study_at, now())) <= 30 AND deleted_at is NULL AND study_at >= now() AND note_summary is NULL", nativeQuery = true)
    List<Meeting> findUpcomingMeetings();

    @Query(value = "SELECT s.study_id id, s.title title, s.category category, s.background_image backgroundImage, future_meeting futureMeeting, past_meeting pastMeeting " +
            "FROM study s LEFT JOIN (" +
            "SELECT study_id, MIN(CASE WHEN study_at >= NOW() THEN study_at ELSE null END) AS future_meeting, " +
            "MAX(CASE WHEN study_at < NOW() THEN study_at ELSE  null END) AS past_meeting FROM meeting m GROUP BY study_id) m " +
            "ON s.study_id = m.study_id WHERE s.study_id in :studies ORDER BY NOW() - m.future_meeting DESC, " +
            "m.past_meeting - NOW() DESC; ", nativeQuery = true)
    List<NearMeetingStudyDto> findAllStudyOrderByMeeting(@Param("studies") List<Long> studies, Pageable pageable);

    List<Meeting> findAllByStudyAndDeletedAtIsNullAndRecordFileIsNotNullOrderByStudyAtDesc(Study study);

    List<Meeting> findAllByStudyAndDeletedAtIsNullAndStudyAtBetweenOrderByStudyAtAsc(Study study, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT * FROM Meeting WHERE abs(DATEDIFF(study_at, now())) <= 1 AND deleted_at is NULL", nativeQuery = true)
    List<Meeting> findTomorrowMeetings();
}
