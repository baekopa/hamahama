package com.baekopa.backend.domain.study.repository;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.entity.StudyMember;
import com.baekopa.backend.domain.study.entity.StudyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findAllByStudyAndDeletedAtIsNull(Study study);

    List<StudyMember> findMemberAndTypeAllByStudyIdAndDeletedAtIsNull(Long studyId);

    Optional<StudyMember> findByIdAndDeletedAtIsNull(Long id);

    Optional<StudyMember> findByStudyAndMemberAndTypeAndDeletedAtIsNull(Study study, Member member, StudyMember.StudyMemberType type);

    Optional<StudyMember> findByStudyIdAndMemberIdAndDeletedAtIsNull(Long studyId, Long memberId);

    List<StudyMember> findAllByMemberAndDeletedAtIsNull(Member member);

    @Query("SELECT sm.study FROM StudyMember sm WHERE sm.member = :member AND sm.type <> :type AND sm.study.type = :studyType AND sm.study.deletedAt is null")
    List<Study> findStudyAllByMemberAndTypeIsNot(@Param(value = "member") Member member, @Param(value = "type") StudyMember.StudyMemberType type, @Param("studyType") StudyType studyType);

    @Query("SELECT sm.study FROM StudyMember sm WHERE sm.member = :member AND sm.study.type = :studyType AND sm.study.deletedAt is null")
    Optional<Study> findPersonalStudy(@Param("member") Member member, @Param("studyType") StudyType studyType);

    @Query("SELECT sm FROM StudyMember sm WHERE sm.member = :member AND sm.study = :study AND sm.type != :type AND sm.deletedAt is null")
    Optional<StudyMember> findStudyMemberNotInvitation(@Param(value = "member") Member member, @Param(value="study") Study study, @Param(value = "type") StudyMember.StudyMemberType type);
    
    @Modifying
    @Query(value = "DELETE FROM study_member WHERE MONTH(deleted_at) = :thresholdDate", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);
}

