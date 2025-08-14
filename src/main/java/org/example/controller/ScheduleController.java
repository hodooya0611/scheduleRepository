package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.mapper.ScheduleMapper;
import org.example.request.ScheduleRequest;
import org.example.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @PostMapping
    public Schedule createSchedule(@RequestBody ScheduleRequest schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto findSchedule(@PathVariable Long id) {
        var scheduleResult = scheduleService.findSchedule(id);
        return scheduleMapper.toScheduleResponseDto(scheduleResult);
    }
}
