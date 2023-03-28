package dev.lydtech.tracking.util;

import dev.lydtech.dispatch.message.DispatchPreparing;

import java.util.UUID;

public class TestEventData {

    public static DispatchPreparing buildDispatchPreparingEvent(UUID orderId, String status) {
        return DispatchPreparing.builder()
                .orderId(orderId)
                .build();
    }

}
