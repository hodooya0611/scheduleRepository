package com.spring.service;

import com.spring.dto.ScheduleResponseDto;
import com.spring.domain.Schedule;
import com.spring.request.ScheduleRequest;
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