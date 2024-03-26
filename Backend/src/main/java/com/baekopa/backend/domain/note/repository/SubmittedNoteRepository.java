package com.baekopa.backend.domain.note.repository;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import com.baekopa.backend.domain.note.entity.SubmittedNote;

import java.util.List;

public interface SubmittedNoteRepository extends JpaRepository<SubmittedNote, Long> {
    List<SubmittedNote> findMeetingByNote(Note note);

    boolean existsByNoteAndMeeting(Note existNote, Meeting existMeeting);
}
