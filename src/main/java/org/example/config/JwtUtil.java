package org.example.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 비밀키 (HS256 알고리즘)

    // 토큰 유효시간 (예: 1시간)
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    // 토큰 생성
    public String generateToken(String memberId, String role) {
        return Jwts.builder()
                .setSubject(memberId)              // 토큰 주체
                .claim("role", role)              // 추가 클레임
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
    public String getMemberId(String token) {
        return extractClaims(token).getSubject();
    }

    // 토큰에서 role 가져오기
    public String getRole(String token) {
        return extractClaims(token).get("role", String.class);
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
