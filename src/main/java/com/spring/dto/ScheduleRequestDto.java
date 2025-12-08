package com.spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleRequestDto {

    // 일정 제목
    private String title;

    // 일정 장소
    private String place;

    // 일정 내용
    private String content;

    // 시작 날짜
    private LocalDate startDate;

    // 시작 시간
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    // 종료 날짜
    private LocalDate endDate;

    // 종료 시간
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    // 하루종일 여부
    private boolean allDay;

    // 알람 켜짐/꺼짐
    private boolean alarmEnabled;

    // 5분전, 10분전, 직접입력 등
    private String alarmOption;

    // 직접 입력 알람 날짜
    private LocalDate alarmDate;

    // 직접 입력 알람 시간
    @JsonFormat(pattern = "HH:mm")
    private LocalTime alarmTime;

}
