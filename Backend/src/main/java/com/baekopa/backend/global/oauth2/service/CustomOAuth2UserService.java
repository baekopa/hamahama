package com.baekopa.backend.global.oauth2.service;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.global.oauth2.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * 유저 정보 가져오기
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        }
        else {
            oAuth2Response = null;
            return null;
        }

        // 유저 DB 저장
        String providerCode = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        Member existMember = memberRepository.findByProviderCode(providerCode).orElse(null);
        MemberDTO memberDTO = new MemberDTO();

        if (existMember == null) {
            existMember = newMember(oAuth2Response, providerCode);

            memberDTO.setProviderCode(providerCode);
            memberDTO.setId(existMember.getId());
            memberDTO.setName(oAuth2Response.getName());
            memberDTO.setRole("ROLE_USER");

        } else {
            existMember.updateEmail(oAuth2Response.getEmail());
            existMember.updateName(oAuth2Response.getName());
            existMember.updateImage(oAuth2Response.getProfileImage());

            memberRepository.save(existMember);

            memberDTO.setProviderCode(existMember.getProviderCode());
            memberDTO.setId(existMember.getId());
            memberDTO.setName(oAuth2Response.getName());
            memberDTO.setRole(existMember.getRole());
        }

        return new CustomOAuth2User(memberDTO);
    }

    private Member newMember(OAuth2Response oAuth2Response, String providerCode) {

        Member member = Member.of(oAuth2Response.getName(), providerCode, oAuth2Response.getEmail(), oAuth2Response.getProfileImage(), "ROLE_USER", oAuth2Response.getProvider());

        return memberRepository.save(member);
    }
}
