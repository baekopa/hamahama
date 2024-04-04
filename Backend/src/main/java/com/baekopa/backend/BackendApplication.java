package com.baekopa.backend;

import com.baekopa.backend.domain.member.entity.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling // 스케줄링 기능을 enable 함
@EnableJpaAuditing
@SpringBootApplication
public class BackendApplication {

    // 연결 테스트
    @GetMapping("/api/hello")
    String hello() {
        return "Hello World~";
    }

    @GetMapping("/test")
    String test(@AuthenticationPrincipal Member member) {
        return member.getName();
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
