package com.example.status.handler;

import com.example.status.message.OrderCreated;
import com.example.status.service.StatusService;
import com.example.status.util.TestEventData;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class OrderCreatedHandlerTest {

    private StatusService statusServiceMock;

    private OrderCreatedHandler handler;

    @BeforeEach
    public void setup() {
        statusServiceMock = mock(StatusService.class);
        handler = new OrderCreatedHandler(statusServiceMock);
    }

    @Test
    public void testListen() {
        OrderCreated testEvent = TestEventData.buildOrderCreatedEvent(UUID.randomUUID(), RandomStringUtils.randomAlphabetic(8));
        handler.listen(testEvent);
        verify(statusServiceMock, times(1)).processOrderCreated(testEvent);
    }
}
