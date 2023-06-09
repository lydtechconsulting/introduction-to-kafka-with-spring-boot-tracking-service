package dev.lydtech.tracking.handler;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.dispatch.message.DispatchCompleted;
import dev.lydtech.tracking.service.TrackingService;
import dev.lydtech.tracking.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DispatchTrackingHandlerTest {

    private TrackingService trackingServiceMock;

    private DispatchTrackingHandler handler;

    @BeforeEach
    public void setup() {
        trackingServiceMock = mock(TrackingService.class);
        handler = new DispatchTrackingHandler(trackingServiceMock);
    }

    @Test
    public void listen_DispatchPreparing() throws Exception {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(randomUUID());
        handler.listen(testEvent);
        verify(trackingServiceMock, times(1)).processDispatchPreparing(testEvent);
    }

    @Test
    public void listen_DispatchPreparingException() throws Exception {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(randomUUID());
        doThrow(new RuntimeException("Service failure")).when(trackingServiceMock).processDispatchPreparing(testEvent);

        handler.listen(testEvent);

        verify(trackingServiceMock, times(1)).processDispatchPreparing(testEvent);
    }

    @Test
    public void listen_DispatchCompleted() throws Exception {
        DispatchCompleted testEvent = TestEventData.buildDispatchCompletedEvent(randomUUID(), LocalDate.now().toString());
        handler.listen(testEvent);
        verify(trackingServiceMock, times(1)).processDispatched(testEvent);
    }

    @Test
    public void listen_DispatchCompletedThrowsException() throws Exception {
        DispatchCompleted testEvent = TestEventData.buildDispatchCompletedEvent(randomUUID(), LocalDate.now().toString());
        doThrow(new RuntimeException("Service failure")).when(trackingServiceMock).processDispatched(testEvent);

        handler.listen(testEvent);

        verify(trackingServiceMock, times(1)).processDispatched(testEvent);
    }
}
