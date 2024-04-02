package com.baekopa.backend.domain.member.repository;

import com.baekopa.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderCodeAndDeletedAtIsNull(String providerCode);

    Optional<Member> findByIdAndDeletedAtIsNull(Long id);

    List<Member> findAllByEmailContainsAndDeletedAtIsNull(String query);
}
