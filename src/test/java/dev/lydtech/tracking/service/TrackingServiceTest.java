package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.dispatch.message.Dispatched;
import dev.lydtech.tracking.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Dispatched testEvent = TestEventData.buildDispatchedEvent(UUID.randomUUID());
        service.processDispatched(testEvent);
    }

}
