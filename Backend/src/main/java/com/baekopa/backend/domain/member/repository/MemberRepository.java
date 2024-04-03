package com.baekopa.backend.domain.member.repository;

import com.baekopa.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderCodeAndDeletedAtIsNull(String providerCode);

    Optional<Member> findByIdAndDeletedAtIsNull(Long id);

    List<Member> findAllByEmailContainsAndDeletedAtIsNull(String query);

    @Modifying
    @Query(value = "DELETE FROM member WHERE MONTH(deleted_at) = :thresholdDate", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);
}
