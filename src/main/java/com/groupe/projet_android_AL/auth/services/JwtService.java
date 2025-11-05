package com.groupe.projet_android_AL.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey key;
    private final Duration accessTtl;
    private final Duration refreshTtl;

    public JwtService(
            @Value("${jwt.secret}") String secretBase64,
            @Value("${jwt.access-expiration}") Duration accessTtl,
            @Value("${jwt.refresh-expiration}") Duration refreshTtl
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretBase64.trim());
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTtl = accessTtl;
        this.refreshTtl = refreshTtl;
    }

    public String generateAccessToken(Integer userId) {
        return buildToken(userId, accessTtl, "access", UUID.randomUUID().toString());
    }

    public String generateRefreshToken(Integer userId, String jti) {
        return buildToken(userId, refreshTtl, "refresh", jti);
    }

    public Integer getUserId(String token) {
        return Integer.parseInt(parseToken(token).getPayload().getSubject());
    }

    public String getType(String token) {
        Object v = parseToken(token).getPayload().get("typ");
        return v == null ? null : v.toString();
    }

    public String getJti(String token) {
        return parseToken(token).getPayload().getId();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    private String buildToken(Integer userId, Duration ttl, String typ, String jti) {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(jti)
                .issuedAt(Date.from(now))
                .subject(String.valueOf(userId))
                .expiration(Date.from(now.plus(ttl)))
                .claim("typ", typ)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}
