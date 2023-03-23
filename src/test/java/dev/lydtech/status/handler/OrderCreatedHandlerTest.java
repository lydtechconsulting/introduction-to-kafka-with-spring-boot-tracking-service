package dev.lydtech.status.handler;

import dev.lydtech.status.message.OrderCreated;
import dev.lydtech.status.service.StatusService;
import dev.lydtech.status.util.TestEventData;
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
