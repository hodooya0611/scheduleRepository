package com.spring.service;

import com.spring.domain.Calendar;
import com.spring.domain.CalendarMember;
import com.spring.domain.Member;
import com.spring.domain.enums.CalendarRole;
import com.spring.exception.BusinessException;
import com.spring.exception.ErrorCode;
import com.spring.repository.CalendarMemberRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;
    private final CalendarMemberRepository calendarMemberRepository;



    // 스케쥴 등록하기
    @Override
    public Schedule createSchedule(Long loginUserId, ScheduleRequest scheduleRequest) {

        Member member = memberRepository.findById(loginUserId).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Calendar calendar;

        if(scheduleRequest.calendarId() != null) {
            calendar  = calendarRepository.findById(scheduleRequest.calendarId()).orElseThrow(() -> new BusinessException(ErrorCode.CALENDAR_NOT_FOUND));

            var calendarMember = calendarMemberRepository.existsByCalendarAndMember(calendar,member);

            if(!calendarMember && !calendar.getOwner().getId().equals(loginUserId)) {
                throw new BusinessException(ErrorCode.CALENDAR_ACCESS_DENIED);
            }
        } else {
            calendar = calendarRepository.findByOwnerAndIsDefaultTrue(member)
                    .orElseThrow(() -> new IllegalStateException("기본 캘린더 없음"));
        }

        Schedule schedule = scheduleMapper.toScheduleEntity(scheduleMapper.toScheduleDto(scheduleRequest));

        schedule.setCalendar(calendar);

        return scheduleRepository.save(schedule);
    }

    // 스케쥴 찾기
        @Override
        public List<Schedule> findSchedule(Long loginUserId,String startDate, String endDate, Long calendarId) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Schedule> scheduleList;

        if(calendarId == null) {
            scheduleList = scheduleRepository
                    .findByCalendar_Owner_IdAndStartDateBetween(
                            loginUserId,
                            start,
                            end
                    );
        } else {

         Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new BusinessException(ErrorCode.CALENDAR_NOT_FOUND));

         Member member = memberRepository.findById(loginUserId).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

            var calendarMember = calendarMemberRepository.existsByCalendarAndMember(calendar,member);

            if(!calendarMember && !calendar.getOwner().getId().equals(loginUserId)) {
                throw new BusinessException(ErrorCode.CALENDAR_ACCESS_DENIED);
            }

         scheduleList =  scheduleRepository.findByCalendarAndStartDateBetween(calendar,start,end);
        }

        return scheduleList;
    }

    // 스케쥴 상세보기
    @Override
    public ScheduleDetailResponseDto findSchedule(Long loginUserId, Long id) {

        Schedule schedule = scheduleRepository
                .findByIdAndCalendar_Owner_Id(id, loginUserId)
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 스케줄이 없거나 접근 권한이 없습니다. id=" + id)
                );
        return scheduleMapper.toScheduleResponseDto(schedule);
    }

    // 스케쥴 수정하기
    @Override
    @Transactional
    public void updateSchedule(Long loginUserId,Long scheduleId, ScheduleRequest request) {
        Schedule schedule = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND));

        CalendarMember calendarMember = calendarMemberRepository
                .findByCalendarIdAndMemberId(schedule.getCalendar().getId(), loginUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        );

        if(calendarMember.getRole() == CalendarRole.VIEWER) {
            throw new  BusinessException(ErrorCode.UPDATE_ACCESS_DENIED);
        }

        var scheduleRequestDto = scheduleMapper.toScheduleDto(request);
        schedule.updateSchedule(scheduleRequestDto);
    }

    // 스케쥴 지우기
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
