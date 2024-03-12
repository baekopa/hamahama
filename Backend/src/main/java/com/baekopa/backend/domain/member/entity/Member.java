package com.baekopa.backend.domain.member.entity;

import com.baekopa.backend.global.oauth.provider.OAuthProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    long id;

    String name;

    String email;

    String image;

    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    public void updateImage(String image) {
        this.image = image;
    }

}
