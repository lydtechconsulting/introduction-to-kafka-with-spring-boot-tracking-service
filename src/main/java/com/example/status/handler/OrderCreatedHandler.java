package com.example.status.handler;

import com.example.status.message.OrderCreated;
import com.example.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedHandler {

    @Autowired
    private final StatusService statusService;

    @KafkaListener(
            id = "orderCreatedConsumerClient",
            topics = "order.created",
            groupId = "status.order.created",
            containerFactory = "orderCreatedListenerContainerFactory"
    )
    public void listen(OrderCreated orderCreated) {
        statusService.processOrderCreated(orderCreated);
    }
}
