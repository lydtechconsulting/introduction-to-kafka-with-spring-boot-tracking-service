package dev.lydtech.tracking.util;

import dev.lydtech.dispatch.message.DispatchTracking;

import java.util.UUID;

public class TestEventData {

    public static DispatchTracking buildDispatchTrackingEvent(UUID orderId, String status) {
        return DispatchTracking.builder()
                .orderId(orderId)
                .status(status)
                .build();
    }

}
