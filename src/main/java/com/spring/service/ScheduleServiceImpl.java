package com.spring.service;

import com.spring.domain.Calendar;
import com.spring.domain.Member;
import com.spring.repository.CalendarRepository;
import com.spring.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.spring.dto.ScheduleDetailResponseDto;
import com.spring.domain.Schedule;
import com.spring.mapper.ScheduleMapper;
import com.spring.repository.ScheduleRepository;
import com.spring.request.ScheduleRequest;
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
    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;


    @Override
    public Schedule createSchedule(Long loginUserId, ScheduleRequest scheduleRequest) {

        Member member = memberRepository.findById(loginUserId).orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Calendar calendar;

        if(scheduleRequest.calendarId() != null) {
            calendar = calendarRepository.findByIdAndOwner(scheduleRequest.calendarId(),member).orElseThrow(()
                    ->new IllegalArgumentException("Member not found"));
        } else {
            calendar = calendarRepository.findByOwnerAndIsDefaultTrue(member)
                    .orElseThrow(() -> new IllegalStateException("기본 캘린더 없음"));
        }

        Schedule schedule = scheduleMapper.toScheduleEntity(scheduleMapper.toScheduleDto(scheduleRequest));

        schedule.setCalendar(calendar);

        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> findAllSchedule(Long loginUserId,String startDate, String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return scheduleRepository
                .findByCalendar_Owner_IdAndStartDateBetween(
                        loginUserId,
                        start,
                        end
                );
    }

    @Override
    public ScheduleDetailResponseDto findSchedule(Long loginUserId, Long id) {

        Schedule schedule = scheduleRepository
                .findByIdAndCalendar_Owner_Id(id, loginUserId)
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 스케줄이 없거나 접근 권한이 없습니다. id=" + id)
                );
        return scheduleMapper.toScheduleResponseDto(schedule);
    }

    @Override
    @Transactional
    public void updateSchedule(Long loginUserId,Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository
                .findByIdAndCalendar_Owner_Id(id, loginUserId)
                .orElseThrow(() ->
                        new EntityNotFoundException("수정 권한이 없는 스케줄입니다. id=" + id)
                );

        var scheduleRequestDto = scheduleMapper.toScheduleDto(request);
        schedule.updateSchedule(scheduleRequestDto);

    }

    @Override
    public void deleteSchedule(Long loginUserId,Long id) {
        Schedule schedule = scheduleRepository
                .findByIdAndCalendar_Owner_Id(id, loginUserId)
                .orElseThrow(() ->
                        new EntityNotFoundException("삭제 권한이 없는 스케줄입니다. id=" + id)
                );

        scheduleRepository.delete(schedule);

    }


}
