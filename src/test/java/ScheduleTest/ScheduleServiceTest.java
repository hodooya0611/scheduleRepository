package ScheduleTest;

import org.example.ScheduleApplication;
import org.example.entity.Schedule;
import org.example.request.ScheduleRequest;
import org.example.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ScheduleApplication.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ScheduleServiceTest {

    private final ScheduleService scheduleService;

    public ScheduleServiceTest(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Test
    public void testCreateSchedule() {

        LocalTime nowTrimmed = LocalTime.now().withSecond(0).withNano(0);

        ScheduleRequest request = new ScheduleRequest(  "meeting",
                LocalDate.now(),
                nowTrimmed,
                nowTrimmed.plusHours(1));

        Schedule saved = scheduleService.createSchedule(request);

        System.out.println(" request: " + request);
        assertNotNull(saved.getId());
        System.out.println(" saved ID: " + saved.getId());

    }
}
