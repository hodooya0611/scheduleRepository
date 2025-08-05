package org.example.request;

import java.time.LocalDateTime;

public record ScheduleRequest(
        String title,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

}
