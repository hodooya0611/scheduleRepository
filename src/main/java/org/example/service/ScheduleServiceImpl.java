package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.Schedule;
import org.example.mapper.ScheduleMapper;
import org.example.repository.ScheduleRepository;
import org.example.request.ScheduleRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;


    @Override
    public Schedule createSchedule(ScheduleRequest scheduleRequest) {
        var scheduleRequestDto = scheduleMapper.toScheduleDto(scheduleRequest); // JSON → DTO
        var scheduleRequestEntity = scheduleMapper.toScheduleEntity(scheduleRequestDto);   // DTO → Entity
        return scheduleRepository.save(scheduleRequestEntity);
    }

    @Override
    public List<Schedule> findSchedule(String startDate, String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        var result =scheduleRepository.findByStartDateBetween(start, end);
        return result;
    }

    @Override
    public Schedule updateSchedule(Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found: " + id));

        // 기본 정보
        schedule.setTitle(request.title());
        schedule.setContent(request.content());
        schedule.setAllDay(request.allDay());

        // 날짜/시간 (하루종일이면 시간은 null 처리 가능)
        schedule.setStartDate(request.startDate());
        schedule.setStartTime(request.allDay() ? null : request.startTime());
        schedule.setEndDate(request.endDate());
        schedule.setEndTime(request.allDay() ? null : request.endTime());

        // 알람 관련
        schedule.setAlarmEnabled(request.alarmEnabled());
        schedule.setAlarmOption(request.alarmOption());

        if ("직접입력".equals(request.alarmOption())) {
            schedule.setAlarmDate(request.alarmDate());
            schedule.setAlarmTime(request.alarmTime());
        } else {
            schedule.setAlarmDate(null);
            schedule.setAlarmTime(null);
        }

        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found: " + id));

        scheduleRepository.delete(schedule);
    }


}
