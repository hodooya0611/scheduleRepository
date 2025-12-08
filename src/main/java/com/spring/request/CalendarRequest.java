package com.spring.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record CalendarRequest(

        // 캘린더 제목
        String title,

        // 내용
        String description
) {}