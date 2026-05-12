package com.spring.dto;

import java.time.LocalDate;
import java.util.List;

public record HomeResponseDto(
        List<CalendarSummary> calendarList,

        List<CalendarSummary> sharedCalendarList,

        List<ScheduleSummary> scheduleList,

        List<ScheduleSummary> sharedScheduleList
) {
    public record CalendarSummary(
            Long id,
            String name,
            boolean isDefault
    ) {}

    public record ScheduleSummary(
            Long id,
            String title,
            LocalDate startDate,
            LocalDate endDate
    ) {}
}