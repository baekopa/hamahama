package com.baekopa.backend.domain.study.repository;

import com.baekopa.backend.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    public Optional<Study> findByIdAndDeletedAtIsNull(Long id);

}
