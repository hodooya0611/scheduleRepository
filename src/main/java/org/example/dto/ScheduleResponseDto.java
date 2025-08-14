package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleResponseDto {

    private String title;

    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startDateTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endDateTime;

}
