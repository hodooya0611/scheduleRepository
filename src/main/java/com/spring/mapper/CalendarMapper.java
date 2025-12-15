package com.spring.mapper;

import com.spring.domain.Calendar;
import com.spring.dto.CalendarRequestDto;
import com.spring.request.CalendarRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalendarMapper {

    CalendarRequestDto toCalendarDto (CalendarRequest calendarRequest);

    Calendar toCalendarEntity (CalendarRequest calendarRequest);
}
