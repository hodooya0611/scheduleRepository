package com.spring.service;

import com.spring.dto.ScheduleDetailResponseDto;
import com.spring.domain.Schedule;
import com.spring.request.ScheduleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {

    Schedule createSchedule(Long loginUserId, ScheduleRequest scheduleRequest);

    List<Schedule> findAllSchedule(Long loginUserId,String startDate, String endDate);

    ScheduleDetailResponseDto findSchedule(Long loginUserId, Long id);

    void updateSchedule(Long loginUserId,Long id,ScheduleRequest scheduleRequest);

    void deleteSchedule(Long loginUserId,Long id);
}