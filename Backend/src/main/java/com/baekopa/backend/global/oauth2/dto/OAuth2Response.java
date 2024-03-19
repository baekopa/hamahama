package com.baekopa.backend.global.oauth2.dto;

import com.baekopa.backend.global.oauth.provider.OAuthProvider;

public interface OAuth2Response {

    OAuthProvider getProvider();

    String getProviderId();

    String getEmail();

    String getName();

    String getProfileImage();
}
