package com.baekopa.backend.domain.note.service;

import com.baekopa.backend.domain.meeting.dto.response.SharedMeetingDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.note.dto.request.CreateNoteRequestDto;
import com.baekopa.backend.domain.note.dto.request.CreateNoteSummaryRequestDto;
import com.baekopa.backend.domain.note.dto.request.CreateNoteTailQuestionRequestDto;
import com.baekopa.backend.domain.note.dto.request.UpdateNoteRequestDto;
import com.baekopa.backend.domain.note.dto.response.CreateNoteSummaryResponseDto;
import com.baekopa.backend.domain.note.dto.response.CreateNoteTailQuestionResponseDto;
import com.baekopa.backend.domain.note.dto.response.NoteResponseDto;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;
    private final SubmittedNoteRepository submittedNoteRepository;
    private final RestTemplate restTemplate;

    @Value("${BASE_URL_AI}")
    private String aiUrl;

    public Long createNewNote(CreateNoteRequestDto requestDto) {

        // 작성자
        Member writer = memberRepository.findByIdAndDeletedAtIsNull(requestDto.getWriter())
                .orElseThrow(() -> BusinessException.builder()
                        .errorCode(ErrorCode.MEMBER_ID_NOT_EXIST)
                        .message(ErrorCode.MEMBER_ID_NOT_EXIST.getMessage())
                        .build());

        // 새로운 노트 생성
        Note note = Note.of(requestDto.getTitle(), requestDto.getContent(), null, writer);

        return noteRepository.save(note).getId();

    }

    // 요약 생성
    public String createSummary(Long noteId) throws JsonProcessingException {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND, ErrorCode.NOTE_NOT_FOUND.getMessage()));

        return getNewSummary(note);
    }

    public String getNewSummary(Note note) throws JsonProcessingException {
        // 요약 요청
        String summaryUrl = aiUrl + "/studies/summary";

        // Json 변환
        CreateNoteSummaryRequestDto summaryRequestDto = CreateNoteSummaryRequestDto.from(note.getContent());
        ObjectMapper objectMapper = new ObjectMapper();
        Object summaryData = objectMapper.writeValueAsString(summaryRequestDto);

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestSummaryEntity = new HttpEntity<>(summaryData, headers);

        // api 요청
        CreateNoteSummaryResponseDto createNoteSummaryResponseDto = restTemplate.postForObject(summaryUrl, requestSummaryEntity, CreateNoteSummaryResponseDto.class);

        log.info("요약 결과물 : {}", createNoteSummaryResponseDto.getSummaryText());

        String tailquestionUrl = aiUrl + "/studies/tailquestion";

        // Json 변환
        CreateNoteTailQuestionRequestDto tailQuestionRequestDto = CreateNoteTailQuestionRequestDto.from(note.getContent());
        Object tailQuestionData = objectMapper.writeValueAsString(tailQuestionRequestDto);

        HttpEntity<Object> requestTailQuestionEntity = new HttpEntity<>(tailQuestionData, headers);

        CreateNoteTailQuestionResponseDto createNoteTailQuestionResponseDto = restTemplate.postForObject(tailquestionUrl, requestTailQuestionEntity, CreateNoteTailQuestionResponseDto.class);

        String newSummary = createNoteSummaryResponseDto.getSummaryText() + "\n\n-------------------------------------------------------------------------\n\n" + createNoteTailQuestionResponseDto.getTailQuestion();

        note.updateSummary(newSummary);

        return newSummary;
    }

    // 노트 조회
    public NoteResponseDto getNote(Long noteId, Member member) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND, ErrorCode.NOTE_NOT_FOUND.getMessage()));

        List<SubmittedNote> meetingList = submittedNoteRepository.findByNoteAndDeletedAtIsNull(note);

        // convert entity to dto
        List<SharedMeetingDto> sharedMeetingDtoList = meetingList.stream().map(this::convertToDto).toList();

        return NoteResponseDto.of(note, member, sharedMeetingDtoList);

    }

    private SharedMeetingDto convertToDto(SubmittedNote submittedNote) {

        Meeting meeting = submittedNote.getMeeting();
        Study study = meeting.getStudy();
        return SharedMeetingDto.of(meeting.getId(),
                meeting.getTopic(),
                meeting.getStudyAt(),
                study.getId(),
                study.getTitle(),
                study.getBackgroundImage());

    }


    // 노트 수정
    public Long updateNote(Long noteId, UpdateNoteRequestDto requestDto) {

        Note note = noteRepository.findByIdAndDeletedAtIsNull(noteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND, ErrorCode.NOTE_NOT_FOUND.getMessage()));

        if (requestDto.getTitle() != null) {
            note.updateTitle(requestDto.getTitle());
        }

        if (requestDto.getContent() != null) {
            note.updateContent(requestDto.getContent());
        }

        return note.getId();
    }

    // 노트 삭제
    public void deleteNote(Long noteId) {

        Note note = noteRepository.findByIdAndDeletedAtIsNull(noteId).orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND, ErrorCode.NOTE_NOT_FOUND.getMessage()));

        submittedNoteRepository.findByNoteAndDeletedAtIsNull(note)
                .forEach(submittedNote -> submittedNoteRepository.deleteById(submittedNote.getId()));

        // 노트 삭제
        noteRepository.deleteById(noteId);

    }
}

