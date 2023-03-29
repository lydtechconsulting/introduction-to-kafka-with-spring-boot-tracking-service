package dev.lydtech.tracking.util;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.dispatch.message.Dispatched;

import java.util.UUID;

public class TestEventData {

    public static DispatchPreparing buildDispatchPreparingEvent(UUID orderId) {
        return DispatchPreparing.builder()
                .orderId(orderId)
                .build();
    }

    public static Dispatched buildDispatchedEvent(UUID orderId) {
        return Dispatched.builder()
                .orderId(orderId)
                .build();
    }

}
