package com.baekopa.backend.domain.member.repository;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.global.oauth.provider.OAuthProvider;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndProvider(String email, OAuthProvider oAuthProvider);
}
