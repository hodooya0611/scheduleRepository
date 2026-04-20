package com.spring.domain;

import com.spring.domain.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "member")
@Table(name = "authToken")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType type; // ⭐ 토큰 용도 구분

    private boolean used;

    @Column(columnDefinition = "LONGTEXT")
    private String payload;

    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now();

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }

    public void markUsed() {
        this.used = true;
    }

}

