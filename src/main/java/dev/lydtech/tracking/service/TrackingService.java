package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchTracking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrackingService {

    public void process(DispatchTracking dispatchTracking) {
        log.info("Received dispatch tracking message : " + dispatchTracking);
    }

}
