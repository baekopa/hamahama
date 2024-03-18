package com.baekopa.backend.global.oauth.service;

import com.baekopa.backend.global.oauth.dto.OAuthApiParams;
import com.baekopa.backend.global.oauth.dto.OAuthInfoResponse;
import com.baekopa.backend.global.oauth.provider.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider(); // Client 타입 변환
    String requestAccessToken(OAuthApiParams params); // code를 기반으로 인증 API를 요청해서 Access Token 획득
    OAuthInfoResponse requestOauthInfo(String accessToken); // Access Token을 기반으로 프로필 정보 획득
    void requestUnlink(String accessToken); //
}
