package com.baekopa.backend.domain.member.service;

import com.baekopa.backend.domain.meeting.dto.NearMeetingStudyDto;
import com.baekopa.backend.domain.meeting.dto.response.MeetingListDto;
import com.baekopa.backend.domain.meeting.dto.response.RemindQuizListResponseDto;
import com.baekopa.backend.domain.meeting.dto.response.StudyMeetingListDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.RemindQuiz;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.RemindQuizRepository;
import com.baekopa.backend.domain.meeting.service.RemindQuizService;
import com.baekopa.backend.domain.member.dto.request.MyInfoReqeustDto;
import com.baekopa.backend.domain.member.dto.response.MemberMainResponseDto;
import com.baekopa.backend.domain.member.dto.response.MyDashboardResponseDto;
import com.baekopa.backend.domain.member.dto.response.MyInfoResponseDto;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.note.dto.response.NoteListResponseDto;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.notification.dto.response.NotificationResponseDto;
import com.baekopa.backend.domain.notification.repository.NotificationRepository;
import com.baekopa.backend.domain.study.dto.response.StudyListResponseDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.entity.StudyMember;
import com.baekopa.backend.domain.study.entity.StudyType;
import com.baekopa.backend.domain.study.repository.StudyMemberRepository;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.baekopa.backend.global.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3UploadService s3UploadService;
    private final MeetingRepository meetingRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final NoteRepository noteRepository;
    private final RemindQuizRepository remindQuizRepository;
    private final NotificationRepository notificationRepository;
    private final StudyRepository studyRepository;

    private final RemindQuizService remindQuizService;

    @Transactional(readOnly = true)
    public MyInfoResponseDto getMyInfo(Member currentMember) {
        Member member = memberRepository.findById(currentMember.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_ID_NOT_EXIST, ErrorCode.MEMBER_ID_NOT_EXIST.getMessage()));

        return MyInfoResponseDto.of(member.getName(), member.getEmail(), member.getImage(), member.getProvider());
    }

    @Transactional
    public Map<String, String> updateMyInfo(Member currentMember, MyInfoReqeustDto myInfoRequestDto) throws IOException {

        Member member = memberRepository.findById(currentMember.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_ID_NOT_EXIST, ErrorCode.MEMBER_ID_NOT_EXIST.getMessage()));

        Map<String, String> result = new HashMap<>();

        if (myInfoRequestDto.getName() != null) {

            member.updateName(myInfoRequestDto.getName());
            result.put("name", myInfoRequestDto.getName());

            return result;
        }

        if (myInfoRequestDto.getImage() != null) {

            MultipartFile image = myInfoRequestDto.getImage();
            String imgUrl = s3UploadService.saveFile("images", image);

            // 이전 이미지 삭제
            if (member.getImage().equals(imgUrl)) {
                s3UploadService.deleteFile(member.getImage());
            }

            member.updateImage(imgUrl);
            result.put("imageUrl", imgUrl);

            return result;
        }

        throw new BusinessException(ErrorCode.NOT_VALID_ERROR, ErrorCode.NOT_VALID_ERROR.getMessage());

    }

    // 마이페이지 대시보드 정보 조회
    @Transactional(readOnly = true)
    public MyDashboardResponseDto getMyDashboard(Member member) {

        // 알림 목록 조회 ( 전체 )
        List<NotificationResponseDto> notificationList = notificationRepository.findAllByReceiverAndDeletedAtIsNullOrderByCreatedAtDesc(member)
                .stream().map(NotificationResponseDto::of).toList();

        LocalDateTime weekStartDate = LocalDateTime.now().with(DayOfWeek.MONDAY).minusDays(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime weekEndDate = LocalDateTime.now().with(DayOfWeek.SUNDAY).minusDays(1)
                .withHour(23).withMinute(59).withSecond(59).withNano(0);


        log.info("이번주 시작일 : {} ~ 이번주 종료일 : {}", weekStartDate, weekEndDate);

        // 예정된 미팅 일정 조회
        List<StudyMember> studyMemberList = studyMemberRepository.findAllByMemberAndDeletedAtIsNull(member);
        List<StudyMeetingListDto> weekStudyList = new ArrayList<>();

        for (StudyMember st : studyMemberList) {

            List<MeetingListDto> meetingList = meetingRepository.findAllByStudyAndDeletedAtIsNullAndStudyAtBetweenOrderByStudyAtAsc(st.getStudy(), weekStartDate, weekEndDate)
                    .stream().map(MeetingListDto::from).toList();

            if (meetingList == null) {
                continue;
            }

            weekStudyList.add(StudyMeetingListDto.of(st.getStudy().getId(),
                    st.getStudy().getTitle(),
                    st.getStudy().getDay(),
                    st.getStudy().getStartDate(),
                    st.getStudy().getEndDate(),
                    meetingList));

        }

        // dto 만들기
        return MyDashboardResponseDto.of(notificationList, weekStudyList);

    }

    // 미팅 조회 (노트 내보내기 용)
    @Transactional(readOnly = true)
    public List<StudyMeetingListDto> getStudyMeetings(Member member) {

        List<StudyMember> studyMemberList = studyMemberRepository.findAllByMemberAndDeletedAtIsNull(member);

        List<StudyMeetingListDto> responseList = new ArrayList<>();

        // 내가 속한 스터디의 일정 목록 조회
        for (StudyMember st : studyMemberList) {

            List<MeetingListDto> meetingList = meetingRepository.findAllByStudyAndDeletedAtIsNull(st.getStudy())
                    .stream().map(this::convertToDto).toList();

            if (meetingList.size() == 0) {
                continue;
            }

            responseList.add(StudyMeetingListDto.of(st.getStudy().getTitle(), meetingList));
        }

        return responseList;

    }

    private MeetingListDto convertToDto(Meeting meeting) {
        return MeetingListDto.of(meeting.getId(),
                meeting.getTopic(),
                meeting.getStudyAt());
    }

    // TODO: 일정 조회
//    public List<WeekMeetingListDto> getMyMeetings(Member member, RequestWeekDto requestDto) {
//
//        // TODO: 반복 일정
//
//        // TODO: 실제 Meeting 일정
//
//        return null;
//    }

    // 내 스터디 조회
    @Transactional(readOnly = true)
    public List<StudyListResponseDto> getMyStudies(Member member) {
        List<StudyListResponseDto> allStudies = studyMemberRepository.findAllByMemberAndDeletedAtIsNull(member)
                .stream()
                .map(this::convertToDto)
                .toList();

        // StudyListResponseDto의 id를 기준으로 중복 제거
        List<StudyListResponseDto> uniqueStudies = allStudies.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(StudyListResponseDto::getId, Function.identity(), (existing, replacement) -> existing),
                        map -> new ArrayList<>(map.values())
                ));

        return uniqueStudies;
    }

    @Transactional(readOnly = true)
    public StudyListResponseDto convertToDto(StudyMember studyMember) {

        Study study = studyMember.getStudy();
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime scheduledDate = null;


        // 예정된 미팅 중에서 현재 날짜 이후로 가장 가까운 미팅 조회
        Meeting scheduledMeeting = meetingRepository.findTopByStudyAndDeletedAtIsNullAndStudyAtGreaterThanEqualOrderByStudyAtAsc(study, currentDate).orElse(null);

        // 만약 예정된 미팅이 없다면, 현재 날짜 이전으로 가장 가까운 미팅 조회
        if (scheduledMeeting == null) {
            scheduledMeeting = meetingRepository.findTopByStudyAndDeletedAtIsNullAndStudyAtLessThanEqualOrderByStudyAtDesc(study, currentDate).orElse(null);
        }

        if (scheduledMeeting != null) {
            scheduledDate = scheduledMeeting.getStudyAt();
        }

        return StudyListResponseDto.of(study.getId(), study.getTitle(), study.getBackgroundImage(), scheduledDate, study.getCategory(), study.getType());
    }

    // 내 노트 조회
    @Transactional(readOnly = true)
    public List<NoteListResponseDto> getMyNotes(Member member) {

        return noteRepository.findAllByMember(member).stream().map(this::convertToDto).toList();
    }

    private NoteListResponseDto convertToDto(Note note) {
        return NoteListResponseDto.of(note.getId(), note.getTitle(), note.getCreatedAt(), note.getModifiedAt());
    }

    // 내 리마인드 퀴즈 목록 조회
    @Transactional(readOnly = true)
    public List<RemindQuizListResponseDto> getMyRemindQuiz(Member member) {

        List<StudyMember> studyMemberList = studyMemberRepository.findAllByMemberAndDeletedAtIsNull(member);

        List<RemindQuizListResponseDto> responseDtoList = new ArrayList<>();

        // 내가 속한 스터디의 일정 목록 조회
        for (StudyMember st : studyMemberList) {

            List<Meeting> meetingList = meetingRepository.findAllByStudyAndDeletedAtIsNull(st.getStudy());

            for (Meeting meeting : meetingList) {

                log.info(" 미팅 번호 : {}, 스터디 번호 : {}", meeting.getId(), meeting.getStudy().getId());

                RemindQuiz remindQuiz = remindQuizRepository.findByMeetingAndDeletedAtIsNull(meeting)
                        .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));

                // 현재 시간이 openDate 이전인지 확인
                boolean isOpened = LocalDateTime.now().isAfter(remindQuiz.getOpenDate()) || LocalDateTime.now().isEqual(remindQuiz.getOpenDate());

                responseDtoList.add(RemindQuizListResponseDto.of(remindQuiz.getId(), meeting.getTopic(), st.getStudy().getTitle(), meeting.getStudyAt(),
                        remindQuiz.getOpenDate(), isOpened, remindQuiz.getModifiedAt()));


            }

        }

        return responseDtoList;
    }

    // 리마인드 퀴즈 상세 조회
    public RemindQuizResponseDto getRemindQuiz(Long remindQuizId) {

        RemindQuiz remindQuiz = remindQuizRepository.findByIdAndDeletedAtIsNull(remindQuizId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND, ErrorCode.MEETING_REMIND_QUIZ_NOT_FOUND.getMessage()));

        return RemindQuizResponseDto.of(remindQuiz.getMeeting().getStudy(), remindQuiz.getMeeting(), remindQuiz);

    }


    // 메인 화면 조회용
    public MemberMainResponseDto getMemberMainInfo(Member member) {

        StudyListResponseDto personalStudy = StudyListResponseDto.from(studyMemberRepository.findPersonalStudy(member, StudyType.PERSONAL).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, "개인 스터디 조회에 실패했습니다")));

        List<NoteListResponseDto> noteList = getRecentNotes(member);
        List<NearMeetingStudyDto> studyList = getRecentStudy(member, 5);

        return MemberMainResponseDto.of(personalStudy, noteList, studyList);

    }

    // 내 노트 최근 다섯 개 조회
    @Transactional(readOnly = true)
    public List<NoteListResponseDto> getRecentNotes(Member member) {

        return noteRepository.findTop5ByMemberAndDeletedAtIsNullOrderByModifiedAtDesc(member).stream().map(NoteListResponseDto::from).toList();
    }

    // 내 스터디 최근 몇 개 조회
    @Transactional(readOnly = true)
    public List<NearMeetingStudyDto> getRecentStudy(Member member, int n) {
        List<Long> studies = studyMemberRepository.findStudyAllByMemberAndTypeIsNot(member, StudyMember.StudyMemberType.INVITATION).stream().map(Study::getId).toList();

        return meetingRepository.findAllStudyOrderByMeeting(studies, PageRequest.of(0, n));
    }
}
