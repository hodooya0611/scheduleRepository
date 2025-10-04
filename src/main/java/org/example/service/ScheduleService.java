package org.example.service;

import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.request.ScheduleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {

    Schedule createSchedule(ScheduleRequest scheduleRequest);

    List<Schedule> findAllSchedule(String startDate, String endDate);

    ScheduleResponseDto findSchedule(Long id);

    void updateSchedule(Long id,ScheduleRequest scheduleRequest);

    void deleteSchedule(Long id);
}