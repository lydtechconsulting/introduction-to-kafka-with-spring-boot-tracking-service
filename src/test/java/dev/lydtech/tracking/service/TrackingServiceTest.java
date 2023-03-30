package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.dispatch.message.DispatchCompleted;
import dev.lydtech.tracking.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class TrackingServiceTest {
    private TrackingService service;

    @BeforeEach
    public void setup() {
        service = new TrackingService();
    }

    @Test
    public void testProcessDispatchPreparing() {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        service.processDispatchPreparing(testEvent);
    }

    @Test
    public void testProcessDispatched() {
        DispatchCompleted testEvent = TestEventData.buildDispatchCompletedEvent(UUID.randomUUID(), LocalDate.now().toString());
        service.processDispatched(testEvent);
    }

}
