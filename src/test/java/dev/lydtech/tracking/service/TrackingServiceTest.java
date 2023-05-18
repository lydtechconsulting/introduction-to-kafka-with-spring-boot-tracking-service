package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.tracking.message.TrackingStatus;
import dev.lydtech.tracking.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TrackingServiceTest {

    private KafkaTemplate kafkaProducerMock;
    private TrackingService service;

    @BeforeEach
    public void setup() {
        kafkaProducerMock = mock(KafkaTemplate.class);
        service = new TrackingService(kafkaProducerMock);
    }

    @Test
    public void process_success() throws Exception {
        when(kafkaProducerMock.send(anyString(), any(TrackingStatus.class))).thenReturn(mock(CompletableFuture.class));

        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        service.process(testEvent);

        verify(kafkaProducerMock, times(1)).send(eq("tracking.status"), any(TrackingStatus.class));
    }

    @Test
    public void process_DispatchPreparingProducerThrowsException() {
        doThrow(new RuntimeException("order dispatched producer failure")).when(kafkaProducerMock).send(eq("tracking.status"), any(TrackingStatus.class));

        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        Exception exception = assertThrows(RuntimeException.class, () -> service.process(testEvent));

        verify(kafkaProducerMock, times(1)).send(eq("tracking.status"), any(TrackingStatus.class));
        assertThat(exception.getMessage(), equalTo("order dispatched producer failure"));
    }
}
