package com.baekopa.backend.global.oauth.service;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.global.oauth.dto.OAuthApiParams;
import com.baekopa.backend.global.oauth.dto.OAuthInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthApiService {

    private final MemberRepository memberRepository;
    private final RequestOAuthService requestOAuthService;

    public void login(OAuthApiParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthService.requestInfo(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
//        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmailAndProvider(oAuthInfoResponse.getEmail(), oAuthInfoResponse.getOAuthProvider())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {

        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickname())
                .image(oAuthInfoResponse.getImage())
                .provider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getId();
    }
}
