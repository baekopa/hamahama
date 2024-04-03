package com.baekopa.backend.global.schedule.service;

import com.baekopa.backend.domain.meeting.repository.*;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.domain.note.repository.NoteRepository;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import com.baekopa.backend.domain.notification.repository.NotificationRepository;
import com.baekopa.backend.domain.study.repository.StudyMemberRepository;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class SoftDeleteScheduleService {

    private final MemberRepository memberRepository;
    private final SubmittedNoteRepository submittedNoteRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final RemindQuizRepository remindQuizRepository;
    private final NotificationRepository notificationRepository;
    private final NoteRepository noteRepository;
    private final MeetingSummaryRepository meetingSummaryRepository;
    private final MeetingScriptRepository meetingScriptRepository;
    private final MeetingRepository meetingRepository;
    private final MeetingKeywordRepository meetingKeywordRepository;


    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul")
    @Transactional
    public void cleanSoftDeletedData() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Timestamp thresholdDate = Timestamp.valueOf(thirtyDaysAgo);
        int deletedCount = 0;

        System.out.println("thresholdDate : " + thresholdDate);

        deletedCount += notificationRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        deletedCount += meetingScriptRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        deletedCount += meetingSummaryRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        deletedCount += meetingKeywordRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        deletedCount += remindQuizRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        deletedCount += studyMemberRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        deletedCount += submittedNoteRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        deletedCount += noteRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        deletedCount += meetingRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        deletedCount += studyRepository.deleteSoftDeletedBeforeDate(thresholdDate);
        deletedCount += memberRepository.deleteSoftDeletedBeforeDate(thresholdDate);

        System.out.println("deletedCount : " + deletedCount);
    }
}


