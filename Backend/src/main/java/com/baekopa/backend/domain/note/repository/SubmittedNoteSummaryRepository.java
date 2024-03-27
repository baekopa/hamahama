package com.baekopa.backend.domain.note.repository;

import com.baekopa.backend.domain.note.entity.SubmittedNoteSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmittedNoteSummaryRepository extends JpaRepository<SubmittedNoteSummary, Long> {
}
