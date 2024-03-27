package com.baekopa.backend.domain.member.service;

import com.baekopa.backend.domain.meeting.dto.response.MeetingListDto;
import com.baekopa.backend.domain.meeting.dto.response.StudyMeetingListDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.member.dto.request.MyInfoReqeustDto;
import com.baekopa.backend.domain.member.dto.response.MyInfoResponseDto;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.note.dto.response.NoteListResponseDto;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.study.dto.response.StudyListResponseDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.entity.StudyMember;
import com.baekopa.backend.domain.study.repository.StudyMemberRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.baekopa.backend.global.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public MeetingListDto convertToDto(Meeting meeting) {
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

        return studyMemberRepository.findAllByMemberAndDeletedAtIsNull(member).stream().map(this::convertToDto).toList();

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

        List<NoteListResponseDto> noteList = noteRepository.findAllByMember(member).stream().map(this::convertToDto).toList();
        return noteList;

    }

    public NoteListResponseDto convertToDto(Note note) {
        return NoteListResponseDto.of(note.getId(), note.getTitle(), note.getCreatedAt(), note.getModifiedAt());
    }

}
