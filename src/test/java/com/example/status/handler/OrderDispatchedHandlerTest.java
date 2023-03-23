package com.example.status.handler;

import com.example.status.message.OrderDispatched;
import com.example.status.service.StatusService;
import com.example.status.util.TestEventData;
import org.apache.commons.lang3.RandomStringUtils;
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
