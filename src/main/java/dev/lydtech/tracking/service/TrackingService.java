package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.tracking.message.TrackingStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingService {

    private static final String TRACKING_STATUS_TOPIC = "tracking.status";

    private final KafkaTemplate<String, Object> kafkaProducer;

    public void process(DispatchPreparing dispatchPreparing) throws Exception {
        log.info("Received dispatch preparing message : " + dispatchPreparing);

        TrackingStatus trackingStatus = TrackingStatus.builder()
                .orderId(dispatchPreparing.getOrderId())
                .status(dev.lydtech.tracking.service.TrackingStatus.PREPARING.toString())
                .build();
        kafkaProducer.send(TRACKING_STATUS_TOPIC, trackingStatus).get();
    }

}
