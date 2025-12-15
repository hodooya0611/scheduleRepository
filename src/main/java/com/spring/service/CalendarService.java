package com.spring.service;

import com.spring.domain.Calendar;
import com.spring.request.CalendarRequest;
import org.springframework.stereotype.Service;

@Service
public interface CalendarService {

    Calendar createCalendar(Long loginUserId,CalendarRequest calendarRequest);

}