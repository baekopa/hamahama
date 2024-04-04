package com.baekopa.backend.domain.study.repository;

import com.baekopa.backend.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    public Optional<Study> findByIdAndDeletedAtIsNull(Long id);
    @Modifying
    @Query(value = "DELETE FROM study WHERE MONTH(deleted_at) = :thresholdDate", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);
}
