package com.baekopa.backend.domain.note.service;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.note.dto.request.CreateNoteRequestDto;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;

    public Long createNewNote(CreateNoteRequestDto requestDto) {

        // 작성자
        Member writer = memberRepository.findByIdAndDeletedAtIsNull(requestDto.getWriter())
                .orElseThrow(() -> BusinessException.builder()
                        .errorCode(ErrorCode.MEMBER_ID_NOT_EXIST)
                        .message(ErrorCode.MEMBER_ID_NOT_EXIST.getMessage())
                        .build());

        // 새로운 노트 생성
        Note note = Note.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(writer)
                .build();

        return noteRepository.save(note).getId();

    }
}
