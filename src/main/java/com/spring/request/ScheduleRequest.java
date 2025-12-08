package com.spring.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleRequest(

        // 스케줄 제목
        String title,

        // 장소
        String place,

        // 일정 내용
        String content,

        // 하루종일 여부
        boolean allDay,

        // 시작일
        LocalDate startDate,

        // 시작 시간
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,

        // 종료일
        LocalDate endDate,

        // 종료 시간
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime,

        // 알람 켜짐 여부
        boolean alarmEnabled,

        // 알람 옵션 (5분전, 10분전 등)
        String alarmOption,

        // 직접입력 선택 시 알람 날짜
        LocalDate alarmDate,

        // 직접입력 선택 시 알람 시간
        @JsonFormat(pattern = "HH:mm")
        LocalTime alarmTime
) {}