package com.baekopa.backend.domain.member.entity;

import com.baekopa.backend.global.oauth.provider.OAuthProvider;
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
    Long id;

    String name;

    String email;

    @Column(name = "profile_image")
    String image;

    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    public void updateImage(String image) {
        this.image = image;
    }

    @Builder
    public Member(String name, String email, String image, OAuthProvider provider){
        this.name = name;
        this.email = email;
        this.image = image;
        this.provider = provider;
    }

}
