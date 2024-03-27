package com.baekopa.backend.domain.note.repository;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByMember(Member member);

}
