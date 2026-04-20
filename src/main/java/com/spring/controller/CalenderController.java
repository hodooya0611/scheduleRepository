package com.spring.controller;

import com.spring.domain.Calendar;
import com.spring.request.CalendarRequest;
import com.spring.security.CustomUserDetails;
import com.spring.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calender")
@RequiredArgsConstructor
public class CalenderController {

    private final CalendarService calendarService;

    @PostMapping
    public Calendar createCalendar(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestBody CalendarRequest calendar) {
        Long loginUserId = userDetails.getId();
        return calendarService.createCalendar(loginUserId,calendar);
    }
}
