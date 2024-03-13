package com.baekopa.backend.global.oauth.dto;

import com.baekopa.backend.global.oauth.provider.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    String getImage();
    OAuthProvider getOAuthProvider();
}
