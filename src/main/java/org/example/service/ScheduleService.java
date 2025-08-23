package org.example.service;

import org.example.entity.Schedule;
import org.example.request.ScheduleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {

    Schedule createSchedule(ScheduleRequest scheduleRequest);

    Schedule findSchedule(Long id);

    Schedule updateSchedule(Long id, ScheduleRequest scheduleRequest);

    void deleteSchedule(Long id);
}