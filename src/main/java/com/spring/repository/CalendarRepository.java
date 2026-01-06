package com.spring.repository;

import com.spring.domain.Calendar;
import com.spring.domain.Member;
import com.spring.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findByOwnerAndIsDefaultTrue(Member owner);

    Optional<Calendar> findByOwner_IdAndIsDefaultTrue(Long ownerId);

    Optional<Calendar> findByIdAndOwner(Long calendarId, Member owner);

    List<Calendar> findAllByOwner_Id(Long loginUserId);
}
