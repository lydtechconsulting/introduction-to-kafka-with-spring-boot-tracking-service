package dev.lydtech.tracking.handler;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.dispatch.message.DispatchCompleted;
import dev.lydtech.tracking.service.TrackingService;
import dev.lydtech.tracking.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class DispatchTrackingHandlerTest {

    private TrackingService trackingServiceMock;

    private DispatchTrackingHandler handler;

    @BeforeEach
    public void setup() {
        trackingServiceMock = mock(TrackingService.class);
        handler = new DispatchTrackingHandler(trackingServiceMock);
    }

    @Test
    public void testListenDispatchPreparing() {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        handler.listen(testEvent);
        verify(trackingServiceMock, times(1)).processDispatchPreparing(testEvent);
    }

    @Test
    public void testListenDispatched() {
        DispatchCompleted testEvent = TestEventData.buildDispatchCompletedEvent(UUID.randomUUID(), LocalDate.now().toString());
        handler.listen(testEvent);
        verify(trackingServiceMock, times(1)).processDispatched(testEvent);
    }
}
