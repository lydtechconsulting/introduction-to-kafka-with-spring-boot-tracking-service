package dev.lydtech.tracking.handler;

import dev.lydtech.dispatch.message.DispatchTracking;
import dev.lydtech.tracking.service.TrackingService;
import dev.lydtech.tracking.util.TestEventData;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void testListen() {
        DispatchTracking testEvent = TestEventData.buildDispatchTrackingEvent(UUID.randomUUID(), RandomStringUtils.randomAlphabetic(8));
        handler.listen(testEvent);
        verify(trackingServiceMock, times(1)).process(testEvent);
    }
}
