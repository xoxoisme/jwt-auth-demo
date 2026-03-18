package com.taehyun.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final SecretKey key;
    private final long accessTokenTtlSeconds;
    private final String issuer;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-token-ttl-seconds:3600}") long accessTokenTtlSeconds,
            @Value("${app.jwt.issuer:jwt-auth-demo}") String issuer
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenTtlSeconds = accessTokenTtlSeconds;
        this.issuer = issuer;
    }

    public String createAccessToken(String subjectEmail, String role) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessTokenTtlSeconds);
        return Jwts.builder()
                .issuer(issuer)                     // 누가 발급했는지
                .subject(subjectEmail)              // 토큰의 주인
                .issuedAt(Date.from(now))           // 발급 시각
                .expiration(Date.from(exp))         // 만료 시각
                .claims(Map.of("role", role))   // 추가 정보(권한)
                .signWith(key)                      // 비밀키
                .compact();                         // JWT 문자열 생성
    }

    public Claims parseAndValidate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

