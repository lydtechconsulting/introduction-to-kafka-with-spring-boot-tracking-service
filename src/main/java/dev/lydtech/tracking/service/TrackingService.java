package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchPreparing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrackingService {

    public void process(DispatchPreparing dispatchPreparing) {
        log.info("Received dispatch preparing message : " + dispatchPreparing);
    }

}
