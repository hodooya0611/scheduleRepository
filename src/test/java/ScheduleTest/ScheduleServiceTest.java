package ScheduleTest;

import org.example.dto.ScheduleRequestDto;
import org.example.entity.Schedule;
import org.example.mapper.ScheduleMapper;
import org.example.repository.ScheduleRepository;
import org.example.request.ScheduleRequest;
import org.example.service.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleMapper scheduleMapper;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    public void testCreateSchedule() {

        // given (request)
        LocalTime nowTrimmed = LocalTime.now().withSecond(0).withNano(0);
        ScheduleRequest request = new ScheduleRequest(
                "meeting",
                LocalDate.now(),
                nowTrimmed,
                nowTrimmed.plusHours(1)
        );

        // mapping ( request -> requestDto)
        ScheduleRequestDto dto = new ScheduleRequestDto();
        dto.setTitle(request.title());
        dto.setDate(request.date());
        dto.setStartDateTime(request.startDateTime());
        dto.setEndDateTime(request.endDateTime());

        // mapping ( requestDto -> Entity)
        Schedule savedSchedule = new Schedule();
        savedSchedule.setId(1L);
        savedSchedule.setTitle(dto.getTitle());
        savedSchedule.setDate(dto.getDate());
        savedSchedule.setStartDateTime(dto.getStartDateTime());
        savedSchedule.setEndDateTime(dto.getEndDateTime());

        when(scheduleMapper.toScheduleDto(request)).thenReturn(dto);
        when(scheduleMapper.toScheduleEntity(dto)).thenReturn(savedSchedule);
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(savedSchedule);

        // when
        Schedule result = scheduleService.createSchedule(request);

        // then
        assertNotNull(result.getId());
        System.out.println("Saved ID: " + result.getId());

        // verify 호출 여부 확인
        verify(scheduleMapper).toScheduleDto(request);
        verify(scheduleMapper).toScheduleEntity(dto);
        verify(scheduleRepository).save(savedSchedule);
    }

    @Test
    public void testFindSchedule() {
        // given (request)
        Long scheduleId = 1L;

        LocalTime now = LocalTime.now().withSecond(0).withNano(0);

        Schedule scheduleEntity = new Schedule(scheduleId, "meeting", LocalDate.now(),
                now, now.plusHours(1));

        // Mock 동작 정의
        when(scheduleRepository.findById(scheduleId)).thenReturn(java.util.Optional.of(scheduleEntity));

        // when
        Schedule result = scheduleService.findSchedule(scheduleId);

        // then
        assertNotNull(result,"Schedule should not be null");
        assertEquals(scheduleId, result.getId(), "Schedule ID should match");
        assertEquals("meeting", result.getTitle(),"Schedule title should match");
        System.out.println("FindSchedule Test Result: " + result);
    }
}
