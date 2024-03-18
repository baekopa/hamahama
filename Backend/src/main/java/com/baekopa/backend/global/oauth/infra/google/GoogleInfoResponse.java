package com.baekopa.backend.global.oauth.infra.google;

import com.baekopa.backend.global.oauth.dto.OAuthInfoResponse;
import com.baekopa.backend.global.oauth.provider.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfoResponse implements OAuthInfoResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("email")
    private String email;


    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getProfileImage() {
        return this.picture;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.GOOGLE;
    }
}
