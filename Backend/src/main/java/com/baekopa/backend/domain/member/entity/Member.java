package com.baekopa.backend.domain.member.entity;

import com.baekopa.backend.global.oauth2.dto.OAuthProvider;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String providerCode;

    private String email;

    @Column(name = "profile_image")
    private String image;

    private String role;

    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @Builder
    public Member(String name, String providerCode, String email, String image, String role, OAuthProvider provider) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.provider = provider;
        this.role = role;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateImage(String image) {
        this.image = image;
    }
}
