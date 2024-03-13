package com.baekopa.backend.global.oauth.infra.google;

import com.baekopa.backend.global.oauth.dto.OAuthApiParams;
import com.baekopa.backend.global.oauth.dto.OAuthInfoResponse;
import com.baekopa.backend.global.oauth.provider.OAuthProvider;
import com.baekopa.backend.global.oauth.service.OAuthApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.google.url.auth}")
    private String authUrl;

    @Value("${oauth.google.url.api}")
    private String apiUrl;

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    /**
     * Access Token 요청
     *
     * @param params 인가코드
     * @return Access Token
     */
    @Override
    public String requestAccessToken(OAuthApiParams params) {
        String url = authUrl + "/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        log.warn("body가 제대로 들어갔나요?? : {}", body.get("code"));

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);


        GoogleTokens response = restTemplate.postForObject(url, request, GoogleTokens.class);


        assert response != null;
        return response.getAccessToken();
    }

    /**
     * 사용자 정보 요청
     *
     * @param accessToken
     * @return 사용자 정보
     */
    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v3/userinfo";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("scope", "profile email");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, GoogleInfoResponse.class);
    }

    @Override
    public void requestUnlink(String accessToken) {
    }
}
