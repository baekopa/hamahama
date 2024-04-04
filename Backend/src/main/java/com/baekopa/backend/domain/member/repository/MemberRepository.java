package com.baekopa.backend.domain.member.repository;

import com.baekopa.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndDeletedAtIsNull(Long id);

    Optional<Member> findByProviderCodeAndDeletedAtIsNull(String providerCode);

    List<Member> findByEmailContainsAndDeletedAtIsNull(String query);

    @Query(value = "SELECT * FROM member WHERE MONTH(deleted_at) <= MONTH(:thresholdDate)", nativeQuery = true)
    List<Member> findSoftDeletedBeforeDate(Timestamp thresholdDate);

    @Modifying
    @Query(value = "DELETE FROM member WHERE MONTH(deleted_at)  <= MONTH(:thresholdDate)", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);
}
