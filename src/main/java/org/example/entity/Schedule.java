package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // 일정 내용
    private String content;

    // 날짜 및 시간
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    // 하루종일 여부
    private boolean allDay;

    // 알람 관련
    private boolean alarmEnabled;      // 알람 켜짐/꺼짐
    private String alarmOption;        // 5분전, 10분전, 직접입력 등
    private LocalDate alarmDate;       // 직접 입력 알람일
    private LocalTime alarmTime;       // 직접 입력 알람시간


}
