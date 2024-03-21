package com.baekopa.backend.global.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${jwt.secret-key}") String secret) {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String getUsername(String token) {
        return parseClaims(token)
                .get("username", String.class);
    }

    public String getRole(String token) {
        return parseClaims(token)
                .get("role", String.class);
    }

    public String getCategory(String token) {
        return parseClaims(token)
                .get("category", String.class);
    }

    public String getId(String token) {
        return parseClaims(token)
                .get("id", String.class);
    }

    public Boolean isExpired(String token) {
        return parseClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(String category, String username, String role, String memberId, Long expiredMs) {

        return Jwts.builder()
                .claim("category", category)
                .claim("username", username)
                .claim("role", role)
                .claim("id", memberId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload();

    }
}