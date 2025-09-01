package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleResponseDto {

    private Long id;

    private String title;

    private String content;

    private LocalDate startDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    private LocalDate endDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private boolean allDay;

    // 알람 관련
    private boolean alarmEnabled;      // 알람 켜짐/꺼짐
    private String alarmOption;        // 5분전, 10분전, 직접입력 등
    private LocalDate alarmDate;       // 직접 입력 알람일
    private LocalTime alarmTime;       // 직접 입력 알람시간

}
