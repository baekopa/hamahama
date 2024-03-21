package com.baekopa.backend.domain.member.dto.response;

import com.baekopa.backend.global.oauth2.dto.OAuthProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyInfoResponseDto {

    private String name;
    private String email;
    private String image_url;
    private OAuthProvider provider;

    @Builder
    public MyInfoResponseDto(String name, String email, String image_url, OAuthProvider provider) {
        this.name = name;
        this.email = email;
        this.image_url = image_url;
        this.provider = provider;
    }

}
