package dev.lydtech.status.service;

import dev.lydtech.status.message.OrderCreated;
import dev.lydtech.status.message.OrderDispatched;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StatusService {

    public void processOrderCreated(OrderCreated orderCreated) {
        log.info("Received an order created message: " + orderCreated);
    }

    public void processOrderDispatched(OrderDispatched orderDispatched) {
        log.info("Received an order dispatched message: " + orderDispatched);
    }
}
