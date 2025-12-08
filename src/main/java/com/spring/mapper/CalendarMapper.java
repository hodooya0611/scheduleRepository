package com.spring.mapper;

import com.spring.domain.Calendar;
import com.spring.domain.Schedule;
import com.spring.dto.CalendarRequestDto;
import com.spring.dto.ScheduleRequestDto;
import com.spring.dto.ScheduleResponseDto;
import com.spring.request.CalendarRequest;
import com.spring.request.ScheduleRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarMapper {

    CalendarRequestDto toCalendarDto (CalendarRequest calendarRequest);

    Calendar toCalendarEntity (CalendarRequestDto calendarRequestDto);
}
