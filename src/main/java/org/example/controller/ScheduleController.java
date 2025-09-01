package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.mapper.ScheduleMapper;
import org.example.request.ScheduleRequest;
import org.example.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @PostMapping
    public Schedule createSchedule(@RequestBody ScheduleRequest schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @GetMapping
    public List<ScheduleResponseDto> findSchedule( @RequestParam String startDate,
                                                   @RequestParam String endDate) {
        var scheduleResult = scheduleService.findSchedule(startDate,endDate);
        return scheduleMapper.toScheduleResponseDto(scheduleResult);
    }

    @PostMapping("/update/{id}")
    public Schedule updateSchedule(@RequestBody ScheduleRequest schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
