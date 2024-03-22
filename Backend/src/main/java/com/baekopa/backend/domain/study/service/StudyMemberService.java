package com.baekopa.backend.domain.study.service;

import com.baekopa.backend.domain.member.dto.response.MemberResponseDto;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.study.dto.StudyMemberDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyMemberRepository;
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

    // 전체 멤버(사용자) 리스트 조회
    @Transactional(readOnly = true)
    public List<StudyMemberDto> getStudyMembers(Study study) {

        return studyMemberRepository.findAllByStudyAndDeletedAtIsNull(study).stream().map((sm) -> StudyMemberDto.of(sm.getMember(), sm.getType())).toList();
    }

    // 스터디 소속 멤버 조회
    @Transactional(readOnly = true)
    public List<MemberResponseDto> searchMembers(String query) {

        return memberRepository.findAllByEmailContainsAndDeletedAtIsNull(query).stream().map(MemberResponseDto::from).toList();
    }
}
