package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.dispatch.message.DispatchCompleted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrackingService {

    public void processDispatchPreparing(DispatchPreparing dispatchPreparing) {
        log.info("Received dispatch preparing message : " + dispatchPreparing);
    }

    public void processDispatched(DispatchCompleted dispatchCompleted) {
        log.info("Received dispatched message : " + dispatchCompleted);
    }

}
