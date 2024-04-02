package com.baekopa.backend.domain.note.repository;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByIdAndDeletedAtIsNull(Long noteId);

    List<Note> findAllByMemberAndDeletedAtIsNull(Member member);

    List<Note> findTop5ByMemberAndDeletedAtIsNullOrderByModifiedAtDesc(Member member);

}
