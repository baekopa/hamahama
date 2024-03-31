package com.baekopa.backend.domain.note.service;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.note.dto.request.CreateSubmittedNoteSummaryRequestDto;
import com.baekopa.backend.domain.note.repository.SubmittedNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmittedNoteSummaryService {

    private final MeetingRepository meetingRepository;
    private final SubmittedNoteRepository submittedNoteRepository;

    @Scheduled(fixedRate = 5 * 60 * 1000, zone = "Asia/Seoul")
    @Transactional
    public void createSubmittedNoteSummary() {

        // TODO: 요약을 생성할 시간이 되었는 지 확인
        // 요약 해야하는 미팅 조회
        // study at이 now와 30분이내로 차이가 나고, now보다 크고, 삭제되지 않았고, 요약이 아직 안된 meeting
        List<Meeting> meetingList = meetingRepository.findUpcomingMeetings();

        // 요약 해야하는 제출된 노트 조회
        for (Meeting meeting : meetingList) {

            // TODO: 요약 api 요청에 맞게 data를 변경하세용
            // 1. List 제출된 개인 요약 ( CreateSubmittedNoteSummaryRequestDto엔 originText밖에 없습니다. 변경하셔도 좋아요.)
            List<CreateSubmittedNoteSummaryRequestDto> submittedNoteList = submittedNoteRepository.findAllByMeetingAndDeletedAtIsNull(meeting)
                    .stream().map((submittedNote) -> CreateSubmittedNoteSummaryRequestDto.from(submittedNote.getNote().getSummary())).toList();


            // 2. String 하나로!!
            Map<String, String> summaryRequestData = new HashMap<>();
            summaryRequestData.put("originText", combineSumittedNote(submittedNoteList));

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
