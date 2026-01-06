package com.spring.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 토큰 유효시간 (예: 1시간)
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    // 토큰 생성
    public String generateToken(Long memberId, String role) {
        return Jwts.builder()
                .setSubject(String.valueOf(memberId))              // 토큰 주체
                .setIssuedAt(new Date())          // 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료시간
                .signWith(key)                    // 서명
                .compact();
    }

    // 토큰에서 클레임 추출
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰에서 memberId 가져오기
    public Long getMemberId(String token) {
        return Long.valueOf(extractClaims(token).getSubject());
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
