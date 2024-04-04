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

    List<SubmittedNote> findByNote(Note note);

    List<SubmittedNote> findByMeeting(Meeting meeting);

    List<SubmittedNote> findByNoteAndDeletedAtIsNull(Note note);

    List<SubmittedNote> findByMeetingAndDeletedAtIsNull(Meeting meeting);

    boolean existsByNoteAndDeletedAtIsNull(Note note);

    boolean existsByNoteAndMeetingAndDeletedAtIsNull(Note existNote, Meeting existMeeting);

    @Modifying
    @Query(value = "DELETE FROM submitted_note WHERE MONTH(deleted_at)  <= MONTH(:thresholdDate)", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);

    @Modifying
    @Query(value = "UPDATE submitted_note SET note_id = null WHERE note_id = :noteId", nativeQuery = true)
    void updateNoteIdToNull(Long noteId);
}
