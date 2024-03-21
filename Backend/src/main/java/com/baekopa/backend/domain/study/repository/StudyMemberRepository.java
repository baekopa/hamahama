package com.baekopa.backend.domain.study.repository;

import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findAllByStudyAndDeletedAtIsNull(Study study);
}
