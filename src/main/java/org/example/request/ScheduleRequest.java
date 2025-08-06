package org.example.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ScheduleRequest(
        String title,
        LocalDate date,
        LocalTime startDateTime,
        LocalTime endDateTime

) {

}
