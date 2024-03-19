package com.baekopa.backend.global.oauth2.service;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.global.oauth2.dto.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * 유저 정보 가져오기
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            oAuth2Response = null;
            return null;
        }

        //TODO: 처음 로그인이면 회원가입
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        Member existMember = memberRepository
                .findByEmailAndProvider(oAuth2Response.getEmail(), oAuth2Response.getProvider())
                .orElseGet(() -> (Member) newMember(oAuth2Response, username));

        existMember.updateEmail(oAuth2Response.getEmail()); // 이게 필요한 이유가 있나?
        existMember.updateName(oAuth2Response.getName());

        memberRepository.save(existMember); // update

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole("ROLE_USER");

        return new CustomOAuth2User(userDTO);
    }

    private OAuth2User newMember(OAuth2Response oAuth2Response, String username) {

        Member member = Member.builder()
                .email(oAuth2Response.getEmail())
                .name(oAuth2Response.getName())
                .image(oAuth2Response.getProfileImage())
                .provider(oAuth2Response.getProvider())
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole("ROLE_USER");

        return new CustomOAuth2User(userDTO);
    }
}
