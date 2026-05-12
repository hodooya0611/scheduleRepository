package com.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "owner")
@Table(
        name = "calendar",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"owner_id", "is_default"})
        }
)
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
    @JsonIgnore
    private Member owner;

    // 생성 날짜
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 수정 날짜
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

