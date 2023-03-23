package com.example.status.handler;

import com.example.status.message.OrderDispatched;
import com.example.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderDispatchedHandler {

    @Autowired
    private final StatusService statusService;

    @KafkaListener(
            id = "orderDispatchedConsumerClient",
            topics = "order.dispatched",
            groupId = "status.order.dispatched",
            containerFactory = "orderDispatchedListenerContainerFactory"
    )
    public void listen(OrderDispatched orderDispatched) {
        statusService.processOrderDispatched(orderDispatched);
    }
}
