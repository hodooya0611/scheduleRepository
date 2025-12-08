package com.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "calendar")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 캘린더 이름 (예: 내 캘린더, 커플 캘린더, 팀 프로젝트 캘린더)
    @Column(nullable = false)
    private String name;

    // 캘린더 설명 (선택)
    @Column
    private String description;

    // 캘린더를 생성한 사람 (Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    // 생성 날짜
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 수정 날짜
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

