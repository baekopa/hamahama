package com.baekopa.backend.domain.meeting.service;

import com.baekopa.backend.domain.meeting.dto.request.MeetingScriptRequestDto;
import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.domain.meeting.entity.MeetingScript;
import com.baekopa.backend.domain.meeting.repository.MeetingRepository;
import com.baekopa.backend.domain.meeting.repository.MeetingScriptRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingScriptService {

    private final MeetingRepository meetingRepository;
    private final MeetingScriptRepository meetingScriptRepository;

    public Long saveScript(Long meetingId, MeetingScriptRequestDto dto) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_VALID_ERROR, "유효하지 않은 meetingId"));
        List<MeetingScriptRequestDto.Transcription> transScript = dto.getTranscriptions();
        System.out.println("transScript = " + transScript);
        /**
         * transcript 후처리 후 repository에 저장해야함..
         */
//        MeetingScript meetingScript = Meeting.of(meeting, )
        return meeting.getId();
    }
}
