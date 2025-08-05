package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Schedule;
import org.example.request.ScheduleRequest;
import org.example.service.ScheduleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public Schedule createSchedule(@RequestBody ScheduleRequest schedule) {
        return scheduleService.createSchedule(schedule);
    }
}
