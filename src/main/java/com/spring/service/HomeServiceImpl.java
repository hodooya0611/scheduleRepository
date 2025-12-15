package com.spring.service;

import com.spring.domain.Calendar;
import com.spring.domain.Member;
import com.spring.dto.CalendarSummaryResponse;
import com.spring.dto.HomeResponseDto;
import com.spring.mapper.MemberMapper;
import com.spring.repository.CalendarRepository;
import com.spring.repository.MemberRepository;
import com.spring.repository.ScheduleRepository;
import com.spring.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeServiceImpl implements HomeService {

    private final ScheduleRepository scheduleRepository;
    private final CalendarRepository calendarRepository;

    @Override
    public HomeResponseDto getHomeData(Long loginUserId) {

        // 1️⃣ 캘린더 목록
        List<HomeResponseDto.CalendarSummary> calendars =
                calendarRepository.findByOwnerId(loginUserId)
                        .stream()
                        .map(c -> new HomeResponseDto.CalendarSummary(
                                c.getId(),
                                c.getName(),
                                c.isDefault()
                        ))
                        .toList();

        // 2️⃣ 기본 캘린더
        Calendar defaultCalendar =
                calendarRepository.findByOwnerAndIsDefaultTrue(loginUserId)
                        .orElseThrow(() -> new IllegalStateException("기본 캘린더 없음"));

        // 3️⃣ 기본 캘린더의 스케쥴
        List<HomeResponseDto.ScheduleSummary> schedules =
                scheduleRepository.findByCalendar_Owner_IdAndStartDateBetween(
                                loginUserId,
                                startDate,
                                endDate
                        ).stream()
                        .map(s -> new HomeResponseDto.ScheduleSummary(
                                s.getId(),
                                s.getTitle(),
                                s.getStartDate(),
                                s.getEndDate()
                        ))
                        .toList();

        return new HomeResponseDto(calendars, schedules);
    }


}