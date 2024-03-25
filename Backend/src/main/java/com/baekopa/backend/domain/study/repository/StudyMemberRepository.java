package com.baekopa.backend.domain.study.repository;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findAllByStudyAndDeletedAtIsNull(Study study);

    List<StudyMember> findMemberAndTypeAllByStudyIdAndDeletedAtIsNull(Long studyId);

    Optional<StudyMember> findByIdAndDeletedAtIsNull(Long id);

    Optional<StudyMember> findByStudyAndMemberAndTypeAndDeletedAtIsNull(Study study, Member member, StudyMember.StudyMemberType type);

    Optional<StudyMember> findByStudyIdAndMemberIdAndDeletedAtIsNull(Long studyId, Long memberId);
}
