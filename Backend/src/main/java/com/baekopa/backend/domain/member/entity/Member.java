package com.baekopa.backend.domain.member.entity;

import com.baekopa.backend.global.entity.BaseTime;
import com.baekopa.backend.global.oauth2.dto.OAuthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE member_id = ?")
public class Member extends BaseTime implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "provider_code")
    private String providerCode;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image")
    private String image;

    @Column(name = "role")
    private String role;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @Column(name = "last_notification_event_id")
    private String lastCheckedEventId;

    @Builder
    private Member(String name, String providerCode, String email, String image, String role, OAuthProvider provider, String lastCheckedEventId) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.provider = provider;
        this.providerCode = providerCode;
        this.lastCheckedEventId = lastCheckedEventId;
        this.role = role;
    }

    public static Member of(String name, String providerCode, String email, String image, String role, OAuthProvider provider) {
        return builder()
                .name(name)
                .providerCode(providerCode)
                .email(email)
                .image(image)
                .role(role)
                .provider(provider)
                .lastCheckedEventId(initLastNotificationEventId(email, provider))
                .build();
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

    public static String initLastNotificationEventId(String email, OAuthProvider provider) {

        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(email).append("_").append(provider.name()).append("_").append(System.currentTimeMillis()).toString();
    }

    public void updateLastNotificationEventId(String lastNotificationEventId) {
        this.lastCheckedEventId = lastNotificationEventId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return Long.toString(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
