package com.baekopa.backend.domain.note.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface SubmittedNoteRepository extends JpaRepository<SubmittedNote, Long> {

    List<SubmittedNote> findByNoteAndDeletedAtIsNull(Note note);

    boolean existsByNoteAndMeetingAndDeletedAtIsNull(Note existNote, Meeting existMeeting);

    List<SubmittedNote> findAllByMeetingAndDeletedAtIsNull(Meeting meeting);

    boolean existsByNoteAndDeletedAtIsNull(Note note);

    @Modifying
    @Query(value = "DELETE FROM submitted_note WHERE MONTH(deleted_at) = :thresholdDate", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);
}
