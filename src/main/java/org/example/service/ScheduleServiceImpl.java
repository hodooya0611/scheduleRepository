package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScheduleResponseDto;
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
    public List<Schedule> findAllSchedule(String startDate, String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        var result =scheduleRepository.findByStartDateBetween(start, end);
        return result;
    }

    @Override
    public ScheduleResponseDto findSchedule(Long id) {

        var schedule = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 스케줄을 찾을 수 없습니다. id=" + id));

        return scheduleMapper.toScheduleResponseDto(schedule);
    }

    @Override
    @Transactional
    public void updateSchedule(Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found: " + id));

        var scheduleRequestDto = scheduleMapper.toScheduleDto(request);

        schedule.updateSchedule(scheduleRequestDto);

    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found: " + id));

        scheduleRepository.delete(schedule);
    }


}
