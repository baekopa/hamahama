package com.baekopa.backend.global.schedule.service;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.*;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.note.entity.Note;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.baekopa.backend.domain.notification.repository.NotificationRepository;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyMemberRepository;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SoftDeleteScheduleService {

    private final NoteRepository noteRepository;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private final RemindQuizRepository remindQuizRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final NotificationRepository notificationRepository;
    private final SubmittedNoteRepository submittedNoteRepository;
    private final MeetingScriptRepository meetingScriptRepository;
    private final MeetingSummaryRepository meetingSummaryRepository;
    private final MeetingKeywordRepository meetingKeywordRepository;

    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul")
    public void cleanSoftDeletedData() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Timestamp thresholdDate = Timestamp.valueOf(thirtyDaysAgo);
        int deletedCount = 0;

        // -------------------------- n -------------------------
        deletedCount += notificationRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        deletedCount += meetingScriptRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        deletedCount += meetingSummaryRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        deletedCount += meetingKeywordRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        deletedCount += remindQuizRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        deletedCount += studyMemberRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        deletedCount += submittedNoteRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        // -------------------------- 1 -------------------------
        deletedCount += softDeleteNote(thresholdDate);

        deletedCount += softDeleteMeeting(thresholdDate);

        deletedCount += softDeleteStudy(thresholdDate);
        deletedCount += softDeleteMember(thresholdDate);

        log.info("deletedCount {}", deletedCount);
    }

    private int softDeleteMember(Timestamp thresholdDate) {

        AtomicInteger deletedCount = new AtomicInteger();

        List<Member> members = memberRepository.findSoftDeletedBeforeDate(thresholdDate);

        members.stream().map(Member::getId).forEach(id -> {
            deletedCount.addAndGet(notificationRepository.deleteByReceiver(id));
            deletedCount.addAndGet(studyMemberRepository.deleteByMember(id));
        });

        List<Note> notes = members.stream()
                .flatMap(member -> noteRepository.findByMember(member).stream())
                .peek(note -> note.updateMember(null))
                .collect(Collectors.toList());

        noteRepository.saveAll(notes);

        return memberRepository.deleteSoftDeletedBeforeDate(thresholdDate);
    }

    private int softDeleteStudy(Timestamp thresholdDate) {

        AtomicInteger deletedCount = new AtomicInteger();

        List<Study> studies = studyRepository.findSoftDeletedBeforeDate(thresholdDate);

        studies.forEach(study -> {
            deletedCount.addAndGet(studyMemberRepository.deleteByStudy(study.getId()));
            deletedCount.addAndGet(deleteByMeetings(thresholdDate, meetingRepository.findByStudy(study)));
        });

        return deletedCount.get() + studyRepository.deleteSoftDeletedBeforeDate(thresholdDate);
    }

    private int softDeleteMeeting(Timestamp thresholdDate) {

        List<Meeting> meetings = meetingRepository.findSoftDeletedBeforeDate(thresholdDate);

        return deleteByMeetings(thresholdDate, meetings);
    }

    private int deleteByMeetings(Timestamp thresholdDate, List<Meeting> meetings) {

        AtomicInteger deletedCount = new AtomicInteger();

        List<SubmittedNote> modifiedSubmittedNotes = meetings.stream()
                .flatMap(meeting -> submittedNoteRepository.findByMeeting(meeting).stream())
                .peek(submittedNote -> submittedNote.updateNote(null))
                .collect(Collectors.toList());

        submittedNoteRepository.saveAll(modifiedSubmittedNotes);

        meetings.stream().map(Meeting::getId).forEach(id -> {
            deletedCount.addAndGet(meetingScriptRepository.deleteByMeeting(id));
            deletedCount.addAndGet(meetingSummaryRepository.deleteByMeeting(id));
            deletedCount.addAndGet(remindQuizRepository.deleteByMeeting(id));
            deletedCount.addAndGet(meetingKeywordRepository.deleteByMeeting(id));
        });

        return deletedCount.get() + meetingRepository.deleteSoftDeletedBeforeDate(thresholdDate);
    }

    private int softDeleteNote(Timestamp thresholdDate) {

        List<Note> notes = noteRepository.findSoftDeletedBeforeDate(thresholdDate);

        List<SubmittedNote> modifiedSubmittedNotes = notes.stream()
                .flatMap(note -> submittedNoteRepository.findByNote(note).stream())
                .peek(submittedNote -> submittedNote.updateNote(null))
                .collect(Collectors.toList());

        submittedNoteRepository.saveAll(modifiedSubmittedNotes);

        return noteRepository.deleteSoftDeletedBeforeDate(thresholdDate);
    }
}


