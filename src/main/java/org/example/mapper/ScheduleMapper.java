package org.example.mapper;

import org.example.dto.ScheduleRequestDto;
import org.example.entity.Schedule;
import org.example.request.ScheduleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleRequestDto toScheduleDto (ScheduleRequest scheduleRequest);

    Schedule toScheduleEntity (ScheduleRequestDto scheduleRequestDto);
}
