package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.RemindQuizDTO;
import com.baekopa.backend.domain.meeting.dto.request.CreateMeetingRemindQuizDTO;
import com.baekopa.backend.domain.meeting.dto.response.MeetingRemindQuizResponseDTO;
import com.baekopa.backend.domain.meeting.dto.response.RemindQuizListResponseDto;
import com.baekopa.backend.domain.meeting.dto.response.RemindQuizResponseDto;
import com.baekopa.backend.domain.meeting.entity.IsolationEnum;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingSummary;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingSummaryRepository;
import com.baekopa.backend.domain.meeting.repository.RemindQuizRepository;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.entity.NotificationType;
import com.baekopa.backend.domain.notification.service.EmitterService;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyMemberRepository;
import com.baekopa.backend.domain.study.repository.StudyRepository;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemindQuizService {

    private final MeetingSummaryRepository meetingSummaryRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final RemindQuizRepository remindQuizRepository;
    private final MeetingRepository meetingRepository;
    private final StudyRepository studyRepository;
    private final EmitterService emitterService;

    @Value("${BASE_URL_AI}")
    private String fastUrl;
    private final RestTemplate restTemplate;

    @Transactional
    public synchronized MeetingRemindQuizResponseDTO createMeetingRemindQuiz(Long studyId, Long meetingId) {
        String remindQuizUrl = fastUrl + "/studies/quiz";

        MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
        // todo 읽기 권한 체크 더 하기
        if(meetingSummary.getStatus()== IsolationEnum.USING){
            throw new BusinessException(ErrorCode.MEETING_SUMMARY_CAN_NOT_ACCESS, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage());
        }
        LocalDateTime openDate=null;

        Meeting meeting = meetingRepository.findByIdAndDeletedAtIsNull(meetingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));

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
        remindQuizRepository.save(RemindQuiz.from(meeting, remindQuizDTO));
        remindQuiz.updateRemindQuiz(remindQuizDTO.getQuiz());

        return meetingRemindQuizResponseDTO;
    }

    //@Transactional
    //public MeetingRemindQuizResponseDTO reCreateMeetingRemindQuiz(Long studyId, Long meetingId) {
    //    String remindQuizUrl = fastUrl + "/studies/quiz";
    //
    //    MeetingSummary meetingSummary = meetingSummaryRepository.findByIdAndDeletedAtIsNull(meetingId)
    //            .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_SUMMARY_NOT_FOUND, ErrorCode.MEETING_SUMMARY_NOT_FOUND.getMessage()));
    //
    //    CreateMeetingRemindQuizDTO createMeetingRemindQuizDTO = CreateMeetingRemindQuizDTO.from(meetingSummary);
    //    ObjectMapper objectMapper = new ObjectMapper();
    //    Object jsonText;
    //
    //    try {
    //        jsonText = objectMapper.writeValueAsString(createMeetingRemindQuizDTO);
    //    } catch (JsonProcessingException e) {
    //        throw new RuntimeException(e);
    //    }
    //
    //    // fast api 통신
    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setContentType(MediaType.APPLICATION_JSON);
    //    HttpEntity<Object> requestEntity = new HttpEntity<>(jsonText, headers);
    //
    //    MeetingRemindQuizResponseDTO meetingRemindQuizResponseDTO = restTemplate.postForObject(remindQuizUrl, requestEntity, MeetingRemindQuizResponseDTO.class);
    //
    //    // db 저장
    //    RemindQuiz remindQuiz = remindQuizRepository.findByMeetingAndDeletedAtIsNull(meetingRepository.findByIdAndDeletedAtIsNull(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage())))
    //            .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));
    //
    //    assert meetingRemindQuizResponseDTO != null;
    //    remindQuiz.updateRemindQuiz(meetingRemindQuizResponseDTO.getQuiz());
    //
    //    Meeting meeting = meetingRepository.findById(meetingId)
    //            .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_NOT_FOUND, ErrorCode.MEETING_NOT_FOUND.getMessage()));
    //
    //    return meetingRemindQuizResponseDTO;
    //}

    public List<RemindQuizListResponseDto> getStudyRemindQuiz(Long studyId) {

        Study study = studyRepository.findByIdAndDeletedAtIsNull(studyId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));
        List<Meeting> meetingList = meetingRepository.findByStudyAndDeletedAtIsNullAndRecordFileIsNotNullOrderByStudyAtDesc(study);

        List<RemindQuizListResponseDto> response = new ArrayList<>();

        for (Meeting meeting : meetingList) {

            log.warn("미팅 id : {}", meeting.getId());

            RemindQuiz remindQuiz = remindQuizRepository.findByMeetingAndDeletedAtIsNull(meeting).orElse(null);

            if (remindQuiz == null) {
                continue;
            }

            response.add(RemindQuizListResponseDto.of(remindQuiz.getId(), meeting.getTopic(), meeting.getStudy().getId(), meeting.getStudy().getTitle(),
                    meeting.getStudyAt(), remindQuiz.getOpenDate(),
                    LocalDateTime.now().isAfter(remindQuiz.getOpenDate()) || LocalDateTime.now().isEqual(remindQuiz.getOpenDate()),
                    remindQuiz.getModifiedAt()));

        }

        return response;
    }

    public RemindQuizResponseDto getRemindQuiz(Long studyId, Long remindQuizId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, ErrorCode.STUDY_NOT_EXIST.getMessage()));

        RemindQuiz remindQuiz = remindQuizRepository.findById(remindQuizId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));

        Meeting meeting = remindQuiz.getMeeting();

        return RemindQuizResponseDto.of(study, meeting, remindQuiz);

    }

    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul")
    @Transactional
    public void createRemindQuiz() {

        List<RemindQuiz> upcomingRemindQuizzes = remindQuizRepository.findUpcomingRemindQuizzes();

        for (RemindQuiz remindQuiz : upcomingRemindQuizzes) {

            Meeting meeting = remindQuiz.getMeeting();

            List<Member> memberList = studyMemberRepository.findByStudyAndDeletedAtIsNull(meeting.getStudy()).stream()
                    .map((o) -> (o.getMember())).toList();

            String message = "'" + meeting.getStudy().getTitle() + "' '" + meeting.getTopic() + "' 리마인드 퀴즈 생성이 완료되었습니다.";
            for (Member member : memberList) {
                emitterService.send(member, NotificationType.REMIND, message, remindQuiz.getId() + "");
            }
        }
    }
}