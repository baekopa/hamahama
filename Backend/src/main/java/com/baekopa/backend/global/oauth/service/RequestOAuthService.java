package com.baekopa.backend.global.oauth.service;

import com.baekopa.backend.global.oauth.dto.OAuthApiParams;
import com.baekopa.backend.global.oauth.dto.OAuthInfoResponse;
import com.baekopa.backend.global.oauth.provider.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthService {
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse requestInfo(OAuthApiParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);
        return client.requestOauthInfo(accessToken);
    }

    public void requestUnlink(OAuthApiParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);
        client.requestUnlink(accessToken);
    }

}
