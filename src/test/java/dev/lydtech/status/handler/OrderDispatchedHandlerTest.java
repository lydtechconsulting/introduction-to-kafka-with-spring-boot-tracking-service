package dev.lydtech.status.handler;

import dev.lydtech.status.message.OrderDispatched;
import dev.lydtech.status.service.StatusService;
import dev.lydtech.status.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class OrderDispatchedHandlerTest {

    private StatusService statusServiceMock;

    private OrderDispatchedHandler handler;

    @BeforeEach
    public void setup() {
        statusServiceMock = mock(StatusService.class);
        handler = new OrderDispatchedHandler(statusServiceMock);
    }

    @Test
    public void testListen() {
        OrderDispatched testEvent = TestEventData.buildOrderDispatchedEvent(UUID.randomUUID());
        handler.listen(testEvent);
        verify(statusServiceMock, times(1)).processOrderDispatched(testEvent);
    }
}
