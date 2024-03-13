package com.baekopa.backend.global.oauth.infra.google;

import com.baekopa.backend.global.oauth.dto.OAuthApiParams;
import com.baekopa.backend.global.oauth.provider.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLDecoder;

@Getter
@NoArgsConstructor
public class GoogleApiParams implements OAuthApiParams {
    private String authorizationCode;
    private String redirectUri;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        authorizationCode = URLDecoder.decode(authorizationCode, java.nio.charset.StandardCharsets.UTF_8);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("redirect_uri", redirectUri);
        return body;
    }
}
