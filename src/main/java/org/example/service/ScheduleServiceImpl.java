package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.Schedule;
import org.example.mapper.ScheduleMapper;
import org.example.repository.ScheduleRepository;
import org.example.request.ScheduleRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Schedule not found with id: " + id));
    }

    @Override
    public Schedule updateSchedule(Long id, ScheduleRequest request) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found: " + id));

        schedule.setTitle(request.title());
        schedule.setDate(request.date());
        schedule.setStartDateTime(request.startDateTime());
        schedule.setEndDateTime(request.endDateTime());

        return scheduleRepository.save(schedule);
    }

}
