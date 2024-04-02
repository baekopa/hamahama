package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.RemindQuizDTO;
import com.baekopa.backend.domain.meeting.dto.request.*;
import com.baekopa.backend.domain.meeting.dto.response.*;
import com.baekopa.backend.domain.meeting.entity.*;
import com.baekopa.backend.domain.meeting.repository.*;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.baekopa.backend.global.service.S3UploadService;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingSummaryRepository meetingSummaryRepository;
    private final MeetingScriptRepository meetingScriptRepository;
    private final RemindQuizRepository remindQuizRepository;
    private final MeetingKeywordRepository meetingKeywordRepository;
    private final S3UploadService s3UploadService;
    private final StudyRepository studyRepository;
    private final SubmittedNoteRepository submittedNoteRepository;
    private final NoteRepository noteRepository;

    @Value("${BASE_URL_AI}")
    private String fastUrl;
    private final RestTemplate restTemplate;

    public InStudyMeetingListDTO getMeetingList(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));

        List<Meeting> meetingList = meetingRepository.findAllByStudyAndDeletedAtIsNullAndRecordFileIsNotNullOrderByStudyAtDesc(study);
        List<MeetingStudyDTO> meetingStudyDTOList = new ArrayList<>();
        List<MeetingKeywordDTO> meetingKeywordDTOList = new ArrayList<>();
        List<MeetingMemberInfoDTO> meetingMemberInfoDTOList = new ArrayList<>();

        for (Meeting meeting : meetingList) {
            //미팅 키워드
            List<MeetingKeyword> meetingKeywordList = meetingKeywordRepository.findAllByMeetingAndDeletedAtIsNull(meeting);

            for (MeetingKeyword meetingKeyword : meetingKeywordList) {
                MeetingKeywordDTO meetingKeywordDTO = MeetingKeywordDTO.from(meetingKeyword);
                meetingKeywordDTOList.add(meetingKeywordDTO);
            }

            // 미팅 참여자
            List<SubmittedNote> submittedNoteList = submittedNoteRepository.findAllByMeetingAndDeletedAtIsNull(meeting);
            List<Note> noteList = new ArrayList<>();
            for (SubmittedNote submittedNote : submittedNoteList) {
                noteList.add(submittedNote.getNote());
            }
            for (Note note : noteList) {
                meetingMemberInfoDTOList.add(MeetingMemberInfoDTO.from(note.getMember()));
            }

            meetingStudyDTOList.add(MeetingStudyDTO.of(meeting, meetingKeywordDTOList, meetingMemberInfoDTOList));
        }

        return InStudyMeetingListDTO.of(study, meetingStudyDTOList);
    }

    public MeetingResponseDTO getMeetingResultAll(Long meetingId) {
        // 미팅 정보
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));
        // 미팅 전문
        MeetingScript meetingScript = meetingScriptRepository.findByMeetingAndDeletedAtIsNull(meeting)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SCRIPT_NOT_FOUND, ErrorCode.MEETING_SCRIPT_NOT_FOUND.getMessage()));
        // 미팅 요약
        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        // 미팅 키워드
        List<MeetingKeyword> meetingKeywordList = meetingKeywordRepository.findAllByMeetingAndDeletedAtIsNull(meeting);
        List<MeetingKeywordDTO> meetingKeywordDTOList = new ArrayList<>();
        for (MeetingKeyword meetingKeyword : meetingKeywordList) {
            MeetingKeywordDTO meetingKeywordDTO = MeetingKeywordDTO.from(meetingKeyword);
            meetingKeywordDTOList.add(meetingKeywordDTO);
        }

        // 미팅 참여자
        List<SubmittedNote> submittedNoteList = submittedNoteRepository.findAllByMeetingAndDeletedAtIsNull(meeting);
        List<Note> noteList = new ArrayList<>();
        for (SubmittedNote submittedNote : submittedNoteList) {
            noteList.add(submittedNote.getNote());
        }
        List<MeetingMemberInfoDTO> meetingMemberInfoDTOList = new ArrayList<>();
        for (Note note : noteList) {
            meetingMemberInfoDTOList.add(MeetingMemberInfoDTO.from(note.getMember()));
        }

        return MeetingResponseDTO.of(meeting, meetingScript, meetingSummary, meetingKeywordDTOList, meetingMemberInfoDTOList);
    }

    @Transactional
    public synchronized MeetingSummaryResponseDTO createSummary(Long meetingId) {
        String summaryUrl = fastUrl + "/studies/summary";

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));
        if (meetingSummaryRepository.existsByMeetingAndDeletedAtIsNull(meeting)) {//기존의 미팅 sumary 데이터가 있을 경우
            MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
            if (meetingSummary.getStatus() == IsolationEnum.USING) {//이미 누군가 meetingSummary값을 수정 중이라면
                throw new BusinessException(ErrorCode.MEETING_SUMMARY_CAN_NOT_ACCESS, ErrorCode.MEETING_SUMMARY_CAN_NOT_ACCESS.getMessage());
            }
            //기존의 미팅요약을 삭제
            meetingSummaryRepository.delete(meetingSummary);
            meetingSummaryRepository.flush();
        }
        //새로운 미팅 생성(사용중인값으로 생성)
        MeetingSummary meetingSummary = meetingSummaryRepository.saveAndFlush(MeetingSummary.of(meeting));

        String originalText = meetingScriptRepository.findByMeetingAndDeletedAtIsNull(meetingRepository.findById(meetingId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage())))
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()))
                .getScriptContent();

        originalText = originalText.replace(".", ".\n");

        // Json 변환
        MeetingSummaryRequestDTO meetingSummaryRequestDTO = MeetingSummaryRequestDTO.of(originalText);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;
        try {
            jsonText = objectMapper.writeValueAsString(meetingSummaryRequestDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);

        // db 저장
        MeetingSummaryDTO meetingSummaryDTO = restTemplate.postForObject(summaryUrl, requestEntity, MeetingSummaryDTO.class);
        meetingSummary.updateMeetingSummary(meetingSummaryDTO.getSummaryText());
        MeetingSummaryResponseDTO meetingSummaryResponseDTO = MeetingSummaryResponseDTO.from(meetingSummaryDTO);

        return meetingSummaryResponseDTO;
    }

    public MeetingSummaryResponseDTO getMeetingSummary(Long meetingId) {
        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        if(meetingSummary.getStatus()==IsolationEnum.USING){
            throw new BusinessException(ErrorCode.MEETING_SUMMARY_CAN_NOT_ACCESS, ErrorCode.MEETING_SUMMARY_CAN_NOT_ACCESS.getMessage());
        }

        return MeetingSummaryResponseDTO.getMeetingSummary(meetingSummary);
    }

    @Transactional
    public synchronized MeetingSummaryResponseDTO updateCreateSummary(Long meetingId) {
        String summaryUrl = fastUrl + "/studies/summary";

        String originalText = meetingScriptRepository.findByMeetingAndDeletedAtIsNull(meetingRepository.findById(meetingId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage())))
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()))
                .getScriptContent();
        originalText = originalText.replace(".", ".\n");

        // Json 변환
        MeetingSummaryRequestDTO meetingSummaryRequestDTO = MeetingSummaryRequestDTO.of(originalText);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;
        try {
            jsonText = objectMapper.writeValueAsString(meetingSummaryRequestDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);
        MeetingSummaryDTO meetingSummaryDTO = restTemplate.postForObject(summaryUrl, requestEntity, MeetingSummaryDTO.class);

        // db 저장
        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        meetingSummary.updateMeetingSummary(meetingSummaryDTO.getSummaryText());

        return MeetingSummaryResponseDTO.from(meetingSummaryDTO);
    }

    @Transactional
    public synchronized MeetingSummaryResponseDTO updateMeetingSummary(MeetingSummaryUpdateDTO meetingSummaryUpdateDTO, Long meetingId) {
        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        meetingSummary.setUpdateStatus(IsolationEnum.USING);
        meetingSummaryRepository.flush();

        meetingSummary.updateMeetingSummary(meetingSummaryUpdateDTO.getSummaryText());

        return MeetingSummaryResponseDTO.getMeetingSummary(meetingSummary);
    }

    @Transactional
    public synchronized MeetingRemindQuizResponseDTO createMeetingRemindQuiz(Long meetingId) {
        String remindQuizUrl = fastUrl + "/studies/quiz";
        LocalDateTime openDate = null;

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));

        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        // todo 읽기 권한 체크 더 하기
        if(meetingSummary.getStatus()==IsolationEnum.USING){
            throw new BusinessException(ErrorCode.MEETING_SUMMARY_CAN_NOT_ACCESS, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage());
        }

        if (remindQuizRepository.existsByMeetingAndDeletedAtIsNull(meeting)) { // 리마인드 퀴즈가 이미 존재한다면..?
            RemindQuiz remindQuiz = remindQuizRepository.findByMeetingAndDeletedAtIsNull(meeting)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));
            openDate = remindQuiz.getOpenDate();

            if (remindQuiz.getStatus() == IsolationEnum.USING) {
                throw new BusinessException(ErrorCode.REMIND_QUIZ_CAN_CNT_ACCESS, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage());
            }
            remindQuizRepository.delete(remindQuiz);
            remindQuizRepository.flush();
        }
        if(openDate==null){
            openDate=LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(7);
        }
        RemindQuiz remindQuiz=RemindQuiz.from(meeting, openDate);
        remindQuizRepository.saveAndFlush(remindQuiz);

        CreateMeetingRemindQuizDTO createMeetingRemindQuizDTO = CreateMeetingRemindQuizDTO.from(meetingSummary);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;

        try {
            jsonText = objectMapper.writeValueAsString(createMeetingRemindQuizDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);

        MeetingRemindQuizResponseDTO meetingRemindQuizResponseDTO = restTemplate.postForObject(remindQuizUrl, requestEntity, MeetingRemindQuizResponseDTO.class);

        // db 저장
        RemindQuizDTO remindQuizDTO = RemindQuizDTO.of(meetingRemindQuizResponseDTO.getQuiz());
        //remindQuizRepository.save(RemindQuiz.from(meeting, remindQuizDTO));
        remindQuiz.updateRemindQuiz(remindQuizDTO.getQuiz());

        return meetingRemindQuizResponseDTO;
    }

    @Transactional
    public MeetingRemindQuizResponseDTO reCreateMeetingRemindQuiz(Long meetingId) {
        String remindQuizUrl = fastUrl + "/studies/quiz";

        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));

        CreateMeetingRemindQuizDTO createMeetingRemindQuizDTO = CreateMeetingRemindQuizDTO.from(meetingSummary);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;

        try {
            jsonText = objectMapper.writeValueAsString(createMeetingRemindQuizDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);

        MeetingRemindQuizResponseDTO meetingRemindQuizResponseDTO = restTemplate.postForObject(remindQuizUrl, requestEntity, MeetingRemindQuizResponseDTO.class);

        // db 저장
        RemindQuiz remindQuiz = remindQuizRepository.findByMeetingAndDeletedAtIsNull(meetingRepository.findById(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage())))
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));

        assert meetingRemindQuizResponseDTO != null;
        remindQuiz.updateRemindQuiz(meetingRemindQuizResponseDTO.getQuiz());

        return meetingRemindQuizResponseDTO;
    }

    @Transactional
    public synchronized MeetingKeywordListDTO createMeetingKeyword(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));
        //이미 기존의 키워드가 존재 한다면?

        String originalText = meetingScriptRepository.findByMeetingAndDeletedAtIsNull(meetingRepository.findById(meetingId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage())))
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SCRIPT_NOT_FOUND, ErrorCode.MEETING_SCRIPT_NOT_FOUND.getMessage()))
                .getScriptContent();

        if (meetingKeywordRepository.existsByMeetingAndDeletedAtIsNull(meeting)) {
            List<MeetingKeyword> meetingKeywordList = meetingKeywordRepository.findAllByMeetingAndDeletedAtIsNull(meeting);

            for (MeetingKeyword meetingKeyword : meetingKeywordList) {
                if(meetingKeyword.getStatus()==IsolationEnum.USING)
                    throw new BusinessException(ErrorCode.MEETING_KEYWORD_CAN_NOT_ACCESS, ErrorCode.MEETING_KEYWORD_CAN_NOT_ACCESS.getMessage());
                meetingKeywordRepository.delete(meetingKeyword);
            }
            meetingKeywordRepository.flush();
        }

        String keywordUrl = fastUrl + "/studies/keyword";

        //originalText = originalText.replace(".", ".\n");

        MeetingSummaryRequestDTO meetingSummaryRequestDTO = MeetingSummaryRequestDTO.of(originalText);
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonText;

        try {
            jsonText = objectMapper.writeValueAsString(meetingSummaryRequestDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);

        MeetingKeywordResponseDTO MeetingKeywordResponseDTO = restTemplate.postForObject(keywordUrl, requestEntity, MeetingKeywordResponseDTO.class);


        // db 저장

        for (int i = 0; i < MeetingKeywordResponseDTO.getKeyword().size(); i++) {
            meetingKeywordRepository.saveAndFlush(MeetingKeyword.of(meeting, MeetingKeywordResponseDTO.getKeyword().get(i)));
        }

        List<MeetingKeyword> meetingKeywordList = meetingKeywordRepository.findAllByMeetingAndDeletedAtIsNull(meeting);
        List<MeetingKeywordDTO> meetingKeywordDTOList = new ArrayList<>();
        for (MeetingKeyword meetingKeyword : meetingKeywordList) {
            MeetingKeywordDTO meetingKeywordDTO = MeetingKeywordDTO.from(meetingKeyword);
            meetingKeywordDTOList.add(meetingKeywordDTO);
        }

        return MeetingKeywordListDTO.from(meetingKeywordDTOList);
    }

    public MeetingKeywordListDTO getMeetingKeyword(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));
        List<MeetingKeyword> meetingKeywordList = meetingKeywordRepository.findAllByMeetingAndDeletedAtIsNull(meeting);
        List<MeetingKeywordDTO> meetingKeywordDTOList = new ArrayList<>();

        for (MeetingKeyword meetingKeyword : meetingKeywordList) {
            if(meetingKeyword.getStatus()==IsolationEnum.USING){
                throw new BusinessException(ErrorCode.MEETING_KEYWORD_CAN_NOT_ACCESS, ErrorCode.MEETING_KEYWORD_CAN_NOT_ACCESS.getMessage());
            }
            MeetingKeywordDTO meetingKeywordDTO = MeetingKeywordDTO.from(meetingKeyword);
            meetingKeywordDTOList.add(meetingKeywordDTO);
        }
        return MeetingKeywordListDTO.from(meetingKeywordDTOList);
    }

    @Transactional
    public synchronized MeetingKeywordListDTO updateMeetingKeyword(Long meetingId, UpdateMeetingKeywordListDTO updateMeetingKeywordListDTO) {
        for (UpdateMeetingKeywordDTO updateMeetingKeywordDTO : updateMeetingKeywordListDTO.getUpdateMeetingKeywordList()) {
            MeetingKeyword meetingKeyword = meetingKeywordRepository.findByIdAndDeletedAtIsNull(updateMeetingKeywordDTO.getGroupKeywordId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_KEYWORD_NOT_FOUND, ErrorCode.MEETING_KEYWORD_NOT_FOUND.getMessage()));
            meetingKeyword.updateMeetingKeyword(updateMeetingKeywordDTO.getKeyword());
        }

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));
        List<MeetingKeyword> meetingKeywordList = meetingKeywordRepository.findAllByMeetingAndDeletedAtIsNull(meeting);
        List<MeetingKeywordDTO> meetingKeywordDTOList = new ArrayList<>();

        for (MeetingKeyword meetingKeyword : meetingKeywordList) {
            MeetingKeywordDTO meetingKeywordDTO = MeetingKeywordDTO.from(meetingKeyword);
            meetingKeywordDTOList.add(meetingKeywordDTO);
        }
        return MeetingKeywordListDTO.from(meetingKeywordDTOList);
    }


    public MeetingDifferenceResponseDTO createDifference(Long meetingId, Member member) {

        // SubmittedNote 조회
        SubmittedNote submittedNote = getSubmittedNote(meetingId, member);

        // 내 요약문
        String myNoteSummary = submittedNote.getNote().getSummary();

        // 전체 요약문
        String meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()))
                .getSummaryContent();

        // 두 요약문 함께 전달
        List<String> submittedNoteList = Arrays.asList(myNoteSummary, meetingSummary);

        DifferenceTextListDTO differenceTextListDTO = DifferenceTextListDTO.of(submittedNoteList);
        String requestBody = convertObjectToJson(differenceTextListDTO);

        String differenceUrl = fastUrl + "/studies/difference";

        // fast api 통신
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        MeetingDifferenceResponseDTO meetingDifferenceResponseDTO =
                restTemplate.postForObject(differenceUrl, requestEntity, MeetingDifferenceResponseDTO.class);

        submittedNote.updateDifferenceContent(meetingDifferenceResponseDTO.getDifference());

        return meetingDifferenceResponseDTO;
    }

    private SubmittedNote getSubmittedNote(Long meetingId, Member member) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));

        // Member와 Meeting을 기준으로 SubmittedNote 조회
        return submittedNoteRepository.findAllByMeetingAndDeletedAtIsNull(meeting)
                .stream()
                .filter(submittedNote -> submittedNote.getNote().getMember().getId() == member.getId())
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTE_NOT_FOUND, ErrorCode.NOTE_NOT_FOUND.getMessage()));

    }

    private String convertObjectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PARSE_ERROR, ErrorCode.JSON_PARSE_ERROR.getMessage());
        }
    }
}
