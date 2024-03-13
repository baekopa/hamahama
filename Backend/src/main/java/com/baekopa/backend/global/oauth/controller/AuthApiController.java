package com.baekopa.backend.global.oauth.controller;

import com.baekopa.backend.global.oauth.infra.kakao.KakaoApiParams;
import com.baekopa.backend.global.oauth.infra.naver.NaverApiParams;
import com.baekopa.backend.global.oauth.service.OAuthApiService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthApiController {
    private final OAuthApiService oAuthApiService;

    @PostMapping("/kakao")
    public ResponseEntity<?> loginKakao(@RequestBody KakaoApiParams params) {
        oAuthApiService.login(params);
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.LOGIN_SUCCESS));
    }

    @PostMapping("/naver")
    public ResponseEntity<?> loginNaver(@RequestBody NaverApiParams params) {
        oAuthApiService.login(params);
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.LOGIN_SUCCESS));
    }

}
