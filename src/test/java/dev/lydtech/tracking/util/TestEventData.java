package dev.lydtech.tracking.util;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.dispatch.message.DispatchCompleted;

import java.util.UUID;

public class TestEventData {

    public static DispatchPreparing buildDispatchPreparingEvent(UUID orderId) {
        return DispatchPreparing.builder()
                .orderId(orderId)
                .build();
    }

    public static DispatchCompleted buildDispatchCompletedEvent(UUID orderId, String date) {
        return DispatchCompleted.builder()
                .orderId(orderId)
                .dispatchedDate(date)
                .build();

    }

}
