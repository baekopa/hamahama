package com.baekopa.backend.domain.study.service;

import com.baekopa.backend.domain.member.dto.response.MemberResponseDto;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.study.dto.StudyMemberDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.entity.StudyMember;
import com.baekopa.backend.domain.study.repository.StudyMemberRepository;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    // 전체 멤버(사용자) 리스트 조회
    @Transactional(readOnly = true)
    public List<StudyMemberDto> getStudyMembers(Study study) {
        return studyMemberRepository.findAllByStudyAndDeletedAtIsNull(study).stream().map((sm) -> StudyMemberDto.of(sm.getMember(), sm.getType())).toList();
    }

    @Transactional(readOnly = true)
    public List<StudyMemberDto> getStudyMembers(Long studyId) {
        return studyMemberRepository.findMemberAndTypeAllByStudyIdAndDeletedAtIsNull(studyId).stream().map((sm) -> StudyMemberDto.of(sm.getMember(), sm.getType())).toList();
    }

    // 스터디 소속 멤버 조회
    @Transactional(readOnly = true)
    public List<MemberResponseDto> searchMembers(String query) {

        return memberRepository.findAllByEmailContainsAndDeletedAtIsNull(query).stream().map(MemberResponseDto::from).toList();
    }

    // 스터디 멤버 초대 - 한 명
    public void inviteStudyMember(Long studyId, Long memberId) {

        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_ID_NOT_EXIST, "초대 대상 ID가 올바르지 않습니다."));
        Study study = studyRepository.findByIdAndDeletedAtIsNull(studyId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, "올바르지 않은 studyId."));

        studyMemberRepository.save(StudyMember.createStudyMember(study, member, StudyMember.StudyMemberType.INVITATION));
    }

    // 스터디 멤버 초대 - 여러 명
    public void inviteStudyMembers(Study study, List<Long> memberIds) {

        List<Member> members = memberIds.stream()
                .map((id) -> memberRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_ID_NOT_EXIST, "스터디원 ID가 올바르지 않습니다."))
                ).toList();

        members.forEach(member -> {
            studyMemberRepository.save(StudyMember.createStudyMember(study, member, StudyMember.StudyMemberType.INVITATION));
        });
    }

    // 스터디 초대 승낙
    public void joinStudy(Long invitationId, Member member) {

        StudyMember studyMember = studyMemberRepository.findByIdAndDeletedAtIsNull(invitationId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "올바르지 않은 스터디 초대 요청입니다."));

        // 본인만 승낙 가능
        if (!studyMember.getMember().getId().equals(member.getId())) {
            throw new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "스터디 초대 대상이 아닌 유저입니다.");
        }

        studyMember.updateStudyMemberType(StudyMember.StudyMemberType.STUDY_MEMBER);
    }

    // 스터디 초대 거절
    public void rejectStudyInvitation(Long invitationId, Member member) {

        StudyMember studyMember = studyMemberRepository.findByIdAndDeletedAtIsNull(invitationId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "올바르지 않은 스터디 초대 요청입니다."));

        // 본인만 거절 가능
        if (!studyMember.getMember().getId().equals(member.getId())) {
            throw new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "스터디 초대 대상이 아닌 유저입니다.");
        }

        studyMemberRepository.delete(studyMember);
    }

    // 스터디 초대 취소
    public void deleteStudyInvitation(Long studyId, Long invitationId, Member member) {

        StudyMember studyMember = studyMemberRepository.findByIdAndDeletedAtIsNull(invitationId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "올바르지 않은 스터디 초대 요청입니다."));

        // member가 스터디장인지 확인
        studyMemberRepository.findByStudyAndMemberAndTypeAndDeletedAtIsNull(studyMember.getStudy(), member, StudyMember.StudyMemberType.STUDY_LEADER)
                                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_FORBIDDEN_ERROR, "스터디장만 보낼 수 있는 요청입니다"));

        studyMemberRepository.delete(studyMember);
    }

    // 스터디장 위임
    public void updateStudyLeader(Long studyId, StudyMemberDto requestDto, Member member) {

        StudyMember newLeader = studyMemberRepository.findByStudyIdAndMemberIdAndDeletedAtIsNull(studyId, requestDto.getMemberId()).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "올바르지 않은 스터디원 정보입니다."));

        // member가 스터디장인지 확인
        StudyMember curLeader = studyMemberRepository.findByStudyAndMemberAndTypeAndDeletedAtIsNull(newLeader.getStudy(), member, StudyMember.StudyMemberType.STUDY_LEADER)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_FORBIDDEN_ERROR, "스터디장만 보낼 수 있는 요청입니다"));

        newLeader.updateStudyMemberType(StudyMember.StudyMemberType.STUDY_LEADER);
        curLeader.updateStudyMemberType(StudyMember.StudyMemberType.STUDY_MEMBER);

    }

    // 스터디 나가기
    public void deleteStudyMember(Long studyId, Member member) {

        StudyMember studyMember = studyMemberRepository.findByStudyIdAndMemberIdAndDeletedAtIsNull(studyId, member.getId()).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "올바르지 않은 스터디원 정보입니다."));

        // 스터디장은 권한 위임 후 나가기 가능
        if (studyMember.getType().equals(StudyMember.StudyMemberType.STUDY_LEADER)) {
            throw new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "스터디장 권한 위임 후 스터디를 나갈 수 있습니다.");
        }

        studyMemberRepository.delete(studyMember);
    }

    // 스터디에서 강퇴
    public void deleteStudyMember(Long studyId, Long memberId, Member leader) {

        StudyMember studyMember = studyMemberRepository.findByStudyIdAndMemberIdAndDeletedAtIsNull(studyId, memberId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_NOT_EXIST, "올바르지 않은 스터디원 정보입니다."));

        // member가 스터디장인지 확인
        studyMemberRepository.findByStudyAndMemberAndTypeAndDeletedAtIsNull(studyMember.getStudy(), leader, StudyMember.StudyMemberType.STUDY_LEADER)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_FORBIDDEN_ERROR, "스터디장만 보낼 수 있는 요청입니다"));

        studyMemberRepository.delete(studyMember);
    }
}
