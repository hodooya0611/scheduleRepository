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
@ToString(exclude = {"calendar", "member"})
@Table(
        name = "calendar_member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"calendar_id", "member_id"})
        }
)
public class CalendarMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 참여할 캘린더
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    // 참여하는 멤버
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 권한: OWNER / EDITOR / VIEWER 등
    @Column(nullable = false)
    private String role;

    private LocalDateTime invitedAt;

    @PrePersist
    public void prePersist() {
        this.invitedAt = LocalDateTime.now();
    }

}

