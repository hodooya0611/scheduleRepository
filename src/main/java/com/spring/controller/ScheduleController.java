package com.spring.controller;

import com.spring.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import com.spring.dto.ScheduleDetailResponseDto;
import com.spring.domain.Schedule;
import com.spring.mapper.ScheduleMapper;
import com.spring.request.ScheduleRequest;
import com.spring.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @PostMapping
    public Schedule createSchedule(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @RequestBody ScheduleRequest schedule) {
        return scheduleService.createSchedule(userDetails.getId(),schedule);
    }

    @GetMapping
    public List<ScheduleDetailResponseDto> findAllSchedule(
            @AuthenticationPrincipal CustomUserDetails userDetails,@RequestParam String startDate,
                                                     @RequestParam String endDate, @RequestParam(required = false) Long calendarId) {
        var scheduleResult = scheduleService.findSchedule(userDetails.getId(),startDate,endDate, calendarId);
        return scheduleMapper.toScheduleListResponseDto(scheduleResult);
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<ScheduleDetailResponseDto> findSchedule(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id) {

        var schedule = scheduleService.findSchedule(userDetails.getId(),id);

        return ResponseEntity.ok(schedule);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateSchedule(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @RequestBody ScheduleRequest scheduleRequest) {
        scheduleService.updateSchedule(userDetails.getId(),id, scheduleRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@AuthenticationPrincipal CustomUserDetails userDetails,@PathVariable Long id) {
        scheduleService.deleteSchedule(userDetails.getId(),id);
        return ResponseEntity.noContent().build();
    }

}
