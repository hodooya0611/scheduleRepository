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
    public List<ScheduleResponseDto> findAllSchedule(@RequestParam String startDate,
                                                     @RequestParam String endDate) {
        var scheduleResult = scheduleService.findAllSchedule(startDate,endDate);
        return scheduleMapper.toScheduleListResponseDto(scheduleResult);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ScheduleResponseDto> findSchedule(@PathVariable Long id) {

        var schedule = scheduleService.findSchedule(id);

        return ResponseEntity.ok(schedule);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequest scheduleRequest) {
        scheduleService.updateSchedule(id, scheduleRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
