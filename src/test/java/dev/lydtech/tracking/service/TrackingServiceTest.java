package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchPreparing;
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
    public void testProcess() {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        service.process(testEvent);
    }
}
