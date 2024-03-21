package com.baekopa.backend.domain.study.service;

import com.baekopa.backend.domain.study.dto.StudyMemberDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;

    // 스터디 멤버 리스트 조회
    public List<StudyMemberDto> getStudyMembers(Study study) {

        return studyMemberRepository.findAllByStudyAndDeletedAtIsNull(study).stream().map((sm) -> StudyMemberDto.of(sm.getMember(), sm.getType())).toList();
    }
}
