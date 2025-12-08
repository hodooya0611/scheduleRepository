package com.spring.service;

import com.spring.domain.Calendar;
import com.spring.domain.Member;
import com.spring.domain.calendar;
import com.spring.dto.calendarResponseDto;
import com.spring.mapper.CalendarMapper;
import com.spring.mapper.calendarMapper;
import com.spring.repository.CalendarRepository;
import com.spring.repository.MemberRepository;
import com.spring.repository.calendarRepository;
import com.spring.request.CalendarRequest;
import com.spring.request.calendarRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;

    private final CalendarMapper calendarMapper;


    @Override
    public Calendar createCalendar(CalendarRequest calendarRequest) {

        // 1. 로그인한 사용자(memberId)로 Member 객체 조회
        Member owner = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        var calendarRequestDto = calendarMapper.toCalendarDto(calendarRequest); // JSON → DTO
        var calendarRequestEntity = calendarMapper.toCalendarEntity(calendarRequestDto);   // DTO → Entity
        return calendarRepository.save(calendarRequestEntity);
    }
}
