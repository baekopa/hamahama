package com.baekopa.backend.global.oauth.dto;

import com.baekopa.backend.global.oauth.provider.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getName();
    String getProfileImage();
    OAuthProvider getOAuthProvider();
}
