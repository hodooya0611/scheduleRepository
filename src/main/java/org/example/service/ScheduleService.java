package org.example.service;

import org.example.entity.Schedule;
import org.example.request.ScheduleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {

    Schedule createSchedule(ScheduleRequest scheduleRequest);

    List<Schedule> findSchedule(String startDate, String endDate);

    Schedule updateSchedule(Long id, ScheduleRequest scheduleRequest);

    void deleteSchedule(Long id);
}