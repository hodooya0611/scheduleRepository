package com.spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CalendarRequestDto {

    // 캘린더 이름
    private String name;

    // 캘린더 설명
    private String description;
}
