package com.baekopa.backend.domain.note.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmittedNoteRepository extends JpaRepository<SubmittedNote, Long> {

    List<SubmittedNote> findMeetingByNote(Note note);

    boolean existsByNoteAndMeeting(Note existNote, Meeting existMeeting);

    List<SubmittedNote> findAllByMeetingAndDeletedAtIsNull(Meeting meeting);

    boolean existsByNoteAndDeletedAtIsNull(Note note);
}
