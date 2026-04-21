package com.spring.service;

import com.spring.domain.Calendar;
import com.spring.domain.CalendarMember;
import com.spring.domain.Member;
import com.spring.dto.CalendarSummaryResponse;
import com.spring.dto.HomeResponseDto;
import com.spring.exception.BusinessException;
import com.spring.exception.ErrorCode;
import com.spring.mapper.MemberMapper;
import com.spring.repository.CalendarMemberRepository;
import com.spring.repository.CalendarRepository;
import com.spring.repository.MemberRepository;
import com.spring.repository.ScheduleRepository;
import com.spring.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeServiceImpl implements HomeService {

    private final ScheduleRepository scheduleRepository;
    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;
    private final CalendarMemberRepository calendarMemberRepository;

    @Override
    public HomeResponseDto getHomeData(Long loginUserId) {

        // 1️⃣ 캘린더 목록 (사이드바에 표시되는거 내가 만든 모든 나의 캘린더들)
        List<HomeResponseDto.CalendarSummary> calendars =
                calendarRepository.findAllByOwner_Id(loginUserId)
                        .stream()
                        .map(c -> new HomeResponseDto.CalendarSummary(
                                c.getId(),
                                c.getName(),
                                c.isDefault()
                        ))
                        .toList();


        // 3️⃣ 기본 캘린더의 스케쥴
        LocalDate now = LocalDate.now();

        // 이번 달 1일
        LocalDate startDate = now.withDayOfMonth(1);

        // 이번 달 마지막 날
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());


        // 2️⃣ 기본 캘린더 (홈에 보여지는 기본캘린더의 스케쥴들)
        Calendar defaultCalendar =
                calendarRepository.findByOwner_IdAndIsDefaultTrue(loginUserId)
                        .orElseThrow(() -> new IllegalStateException("기본 캘린더 없음"));


        List<HomeResponseDto.ScheduleSummary> schedules =
                scheduleRepository.findByCalendar_Owner_IdAndStartDateBetween(
                                defaultCalendar.getId(),
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



        // 내가 속한 모든 공유캘린더들 목록(사이드바에 표시될것들) 가져오기 (내가만든거는 공유던 아니던 무조건 위에서 가져오니까 필터로 거르기)
        var member = memberRepository.findById(loginUserId).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        var sharedCalendars = calendarMemberRepository.findByMember(member).stream()
                .map(CalendarMember::getCalendar)
                .filter(calendar -> !calendar.getOwner().getId().equals(loginUserId))
                .map(calendar -> new HomeResponseDto.CalendarSummary(calendar.getId(),calendar.getName(),calendar.isDefault()))
                .toList();

        return new HomeResponseDto(calendars,sharedCalendars,schedules);
    }
}