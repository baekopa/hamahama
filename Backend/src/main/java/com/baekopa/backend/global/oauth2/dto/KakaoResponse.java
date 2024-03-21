package com.baekopa.backend.global.oauth2.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attribute.get("kakao_account");
        return account.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> account = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        return profile.get("nickname").toString();
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> account = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        return profile.get("profile_image_url").toString();
    }
}
