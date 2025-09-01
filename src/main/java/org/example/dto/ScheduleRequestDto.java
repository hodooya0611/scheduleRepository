package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleRequestDto {

    private String title;           // 일정 제목
    private String content;         // 일정 내용

    private LocalDate startDate;    // 시작 날짜
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;    // 시작 시간

    private LocalDate endDate;      // 종료 날짜
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;      // 종료 시간

    private boolean allDay;         // 하루종일 여부

    // 알람 관련
    private boolean alarmEnabled;   // 알람 켜짐/꺼짐
    private String alarmOption;     // 5분전, 10분전, 직접입력 등
    private LocalDate alarmDate;    // 직접 입력 알람 날짜
    @JsonFormat(pattern = "HH:mm")
    private LocalTime alarmTime;    // 직접 입력 알람 시간

}
