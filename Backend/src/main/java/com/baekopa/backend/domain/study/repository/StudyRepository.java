package com.baekopa.backend.domain.study.repository;

import com.baekopa.backend.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
