package com.spring.repository;

import com.spring.domain.Calendar;
import com.spring.domain.CalendarMember;
import com.spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarMemberRepository extends JpaRepository<CalendarMember, Long> {

    boolean existsByCalendarAndMember(Calendar calendar,Member member);

}
