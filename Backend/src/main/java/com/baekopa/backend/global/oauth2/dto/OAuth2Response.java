package com.baekopa.backend.global.oauth2.dto;

public interface OAuth2Response {

    OAuthProvider getProvider();

    String getProviderId();

    String getEmail();

    String getName();

    String getProfileImage();
}
