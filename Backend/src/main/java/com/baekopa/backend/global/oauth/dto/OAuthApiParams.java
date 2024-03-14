package com.baekopa.backend.global.oauth.dto;

import com.baekopa.backend.global.oauth.provider.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthApiParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
