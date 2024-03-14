package com.baekopa.backend.global.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${whitelist}")
    private String[] whiteList;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // click jacking 방지
                .headers(header -> header.frameOptions(frame -> frame.disable()))
                // csrf 설정 비활성화 -> jwt 방식을 사용하기 때문
                .csrf((auth) -> auth.disable())
                // cors 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Form 로그인 방식 비활성화
                .formLogin((auth) -> auth.disable())
                // HTTP Basic 인증 방식 비활성화
                .httpBasic((auth) -> auth.disable())
                // OAuth2
                .oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(whiteList).permitAll()
                        .anyRequest().authenticated())
                // RESTful API
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }


    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();

            // FRONT 주소 허용
            config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
            // 모든 REST Method 허용
            config.setAllowedMethods(Collections.singletonList("*"));
            // credential 값 허용
            config.setAllowCredentials(true);
            // 모든 header 허용
            config.setAllowedHeaders(Collections.singletonList("*"));
            // preflight 요청의 결과를 캐시할 시간 지정
            config.setMaxAge(3600L);

            // 브라우저에 response header에 포함할 목록
            config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
            config.setExposedHeaders(Collections.singletonList("Authorization"));
            return config;
        };
    }

}