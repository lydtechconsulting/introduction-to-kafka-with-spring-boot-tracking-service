package dev.lydtech.status.util;

import dev.lydtech.status.message.OrderCreated;
import dev.lydtech.status.message.OrderDispatched;

import java.util.UUID;

public class TestEventData {

    public static OrderCreated buildOrderCreatedEvent(UUID orderId, String item) {
        return OrderCreated.builder()
                .orderId(orderId)
                .item(item)
                .build();
    }

    public static OrderDispatched buildOrderDispatchedEvent(UUID orderId) {
        return OrderDispatched.builder()
                .orderId(orderId)
                .build();
    }

}
