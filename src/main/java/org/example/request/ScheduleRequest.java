package org.example.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleRequest(
        String title,
        String content,          // 일정 내용
        boolean allDay,          // 하루종일 여부
        LocalDate startDate,     // 시작일
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,     // 시작 시간
        LocalDate endDate,       // 종료일
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime,       // 종료 시간
        boolean alarmEnabled,    // 알람 켜짐 여부
        String alarmOption,      // 알람 옵션 (5분전, 10분전 등)
        LocalDate alarmDate,     // 직접입력 선택 시 알람 날짜
        @JsonFormat(pattern = "HH:mm")
        LocalTime alarmTime      // 직접입력 선택 시 알람 시간
) {}