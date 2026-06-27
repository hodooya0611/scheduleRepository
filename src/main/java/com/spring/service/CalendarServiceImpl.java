package com.spring.service;

import com.spring.domain.Calendar;
import com.spring.domain.CalendarMember;
import com.spring.domain.Member;
import com.spring.domain.enums.CalendarRole;
import com.spring.mapper.CalendarMapper;
import com.spring.repository.CalendarMemberRepository;
import com.spring.repository.CalendarRepository;
import com.spring.repository.MemberRepository;
import com.spring.request.CalendarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;
    private final CalendarMemberRepository calendarMemberRepository;

    private final CalendarMapper calendarMapper;


    @Override
    public Calendar createCalendar(Long loginUserId,CalendarRequest calendarRequest) {

        // 1. 로그인한 사용자(memberId)로 Member 객체 조회
        Member owner = memberRepository.findById(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Calendar calendar = calendarMapper.toCalendarEntity(calendarRequest);
        calendar.setOwner(owner);

        var savedCalendar = calendarRepository.save(calendar);

        CalendarMember ownerMember = CalendarMember.builder()
                .calendar(savedCalendar)
                .member(owner)
                .role(CalendarRole.OWNER)
                .build();

        calendarMemberRepository.save(ownerMember);
        return savedCalendar;
    }
}
