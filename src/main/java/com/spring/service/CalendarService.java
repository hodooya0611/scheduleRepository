package com.spring.service;

import com.spring.domain.Calendar;
import com.spring.domain.Schedule;
import com.spring.dto.ScheduleResponseDto;
import com.spring.request.CalendarRequest;
import com.spring.request.ScheduleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CalendarService {

    Calendar createCalendar(CalendarRequest calendarRequest);

}