package com.baekopa.backend.domain.note.service;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.note.dto.response.CreateSubmittedNoteSummaryResponseDto;
import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmittedNoteSummaryService {

    private final MeetingRepository meetingRepository;
    private final SubmittedNoteRepository submittedNoteRepository;

    // TODO: 30분 마다 검사!
    @Scheduled(fixedRate = 10000, zone = "Asia/Seoul") // fixedRate = 30분?
    @Transactional
    public CreateSubmittedNoteSummaryResponseDto createSubmittedNoteSummary() {

        log.info("10초 후 실행 => time : " + LocalDateTime.now());

        // TODO: 요약을 생성할 시간이 되었는 지 확인
        // 요약 해야하는 미팅 조회
        // study at이 now와 30분이내로 차이가 나고, now보다 크고, 삭제되지 않았고, 요약이 아직 안된 meeting
        List<Meeting> meetingList = meetingRepository.findUpcomingMeetings();

        // 요약 해야하는 제출된 노트 조회
        for(Meeting meeting : meetingList) {

            log.info("미팅 주제 : {} =======> 시작 시간 : {}" , meeting.getTopic(), meeting.getStudyAt());

            List<SubmittedNote> submittedNoteList = submittedNoteRepository.findAllByMeetingAndDeletedAtIsNull(meeting);

            log.info("제출된 노트 요약은 {} 개 입니다.", submittedNoteList.size());

            // 개인 요약들을 하나로 합치기
            // TODO: 요약 api 요청에 맞게 data를 변경하세용
            Map<String, String> summaryRequestData = new HashMap<>();
            summaryRequestData.put("Key를 입력하세요", combineSumittedNote(submittedNoteList));
            log.info("하나로 합쳐진 요약본... : {}", summaryRequestData.get("Key를 입력하세요"));

            // TODO: 요약 해주세요
            // TODO: 요약을 완료하면 Meeting의 updateNoteSummary() 메서드를 호출해서 update 해주세용

        }

        return null;
    }

    // 개인 요약 text를 하나로 합침
    public String combineSumittedNote(List<SubmittedNote> submittedNoteList) {

        String combindText = "";

        for(SubmittedNote submittedNote : submittedNoteList){

            combindText += submittedNote.getNote().getSummary() + "\n";

        }

        return combindText;
    }

}
