package com.baekopa.backend.domain.note.service;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.note.dto.request.CreateSubmittedNoteSummaryRequestDto;
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

    // TODO: 20분 마다 검사로 변경해야합니당
    // test를 위해서 10초로 설정해둠 (1초 == 1000ms)
    @Scheduled(fixedRate = 1200000, zone = "Asia/Seoul") // fixedRate = 20분 = 20 * 60 * 1000
    @Transactional
    public void createSubmittedNoteSummary() {

        // TODO: 요약을 생성할 시간이 되었는 지 확인
        // 요약 해야하는 미팅 조회
        // study at이 now와 30분이내로 차이가 나고, now보다 크고, 삭제되지 않았고, 요약이 아직 안된 meeting
        List<Meeting> meetingList = meetingRepository.findUpcomingMeetings();

        // 요약 해야하는 제출된 노트 조회
        for (Meeting meeting : meetingList) {

            log.info("[스케쥴링 테스트] 미팅 주제 : {} ==== 시작 시간 : {}", meeting.getTopic(), meeting.getStudyAt());

            // TODO: 요약 api 요청에 맞게 data를 변경하세용
            // 1. List 제출된 개인 요약 ( CreateSubmittedNoteSummaryRequestDto엔 originText밖에 없습니다. 변경하셔도 좋아요.)
            List<CreateSubmittedNoteSummaryRequestDto> submittedNoteList = submittedNoteRepository.findAllByMeetingAndDeletedAtIsNull(meeting)
                    .stream().map((submittedNote) -> CreateSubmittedNoteSummaryRequestDto.from(submittedNote.getNote().getSummary())).toList();

            log.info("[스케쥴링 테스트] 제출된 노트 요약: {} 개", submittedNoteList.size());


            // 2. String 하나로!!
            Map<String, String> summaryRequestData = new HashMap<>();
            summaryRequestData.put("originText", combineSumittedNote(submittedNoteList));
            log.info("[스케쥴링 테스트] 합쳐진 data 확인 : {}", summaryRequestData.get("originText"));

            // TODO: 요약 해주세요
            // TODO: 요약을 완료하면 Meeting의 updateNoteSummary() 메서드를 호출해서 update 해주세용

        }

    }

    // 개인 요약 text를 하나로 합침
    private String combineSumittedNote(List<CreateSubmittedNoteSummaryRequestDto> requestDtoList) {

        String combindText = "";

        for (CreateSubmittedNoteSummaryRequestDto requestDto : requestDtoList) {

            combindText += requestDto.getOriginText() + "\n";

        }

        return combindText;
    }

}
