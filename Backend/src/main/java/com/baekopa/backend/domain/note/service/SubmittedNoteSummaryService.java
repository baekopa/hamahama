package com.baekopa.backend.domain.note.service;

import com.baekopa.backend.domain.note.repository.SubmittedNoteSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmittedNoteSummaryService {

    private final SubmittedNoteSummaryRepository submittedNoteSummaryRepository;

}
