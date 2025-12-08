package com.spring.mapper;

import com.spring.dto.ScheduleRequestDto;
import com.spring.dto.ScheduleResponseDto;
import com.spring.domain.Schedule;
import com.spring.request.ScheduleRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleRequestDto toScheduleDto (ScheduleRequest scheduleRequest);

    Schedule toScheduleEntity (ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> toScheduleListResponseDto(List<Schedule> scheduleEntity);

    ScheduleResponseDto toScheduleResponseDto(Schedule scheduleEntity);
}
