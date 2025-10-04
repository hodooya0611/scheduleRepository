package org.example.mapper;

import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.request.ScheduleRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleRequestDto toScheduleDto (ScheduleRequest scheduleRequest);

    Schedule toScheduleEntity (ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> toScheduleListResponseDto(List<Schedule> scheduleEntity);

    ScheduleResponseDto toScheduleResponseDto(Schedule scheduleEntity);
}
