package com.spring.repository;

import com.spring.domain.Calendar;
import com.spring.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByStartDateBetween(LocalDate start, LocalDate end);

    // 기간 조회 (내 캘린더에 속한 스케줄만)
    List<Schedule> findByCalendar_Owner_IdAndStartDateBetween(
            Long ownerId,
            LocalDate start,
            LocalDate end
    );

    // 단건 조회 (권한 체크 포함)
    Optional<Schedule> findByIdAndCalendar_Owner_Id(
            Long scheduleId,
            Long ownerId
    );

    List<Schedule> findByCalendarAndStartDateBetween(Calendar calendar,
                                                     LocalDate start,
                                                     LocalDate end);

    List<Schedule> findByCalendarInAndStartDateBetween(
            List<Calendar> calendars,
            LocalDate start,
            LocalDate end);

}