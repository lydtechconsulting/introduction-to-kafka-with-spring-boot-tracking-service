package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.dispatch.message.DispatchCompleted;
import dev.lydtech.dispatch.message.TrackingStatusUpdated;
import dev.lydtech.tracking.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class TrackingServiceTest {

    private KafkaTemplate kafkaProducerMock;
    private TrackingService service;

    @BeforeEach
    public void setup() {
        kafkaProducerMock = mock(KafkaTemplate.class);
        service = new TrackingService(kafkaProducerMock);
    }

    @Test

    public void process_DispatchPreparingSuccess() throws Exception {
        when(kafkaProducerMock.send(anyString(), any(TrackingStatusUpdated.class))).thenReturn(mock(CompletableFuture.class));

        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        service.processDispatchPreparing(testEvent);

        verify(kafkaProducerMock, times(1)).send(eq("tracking.status"), any(TrackingStatusUpdated.class));
    }

    @Test
    public void process_DispatchPreparingProducerThrowsException() {
        doThrow(new RuntimeException("dispatch preparing producer failure")).when(kafkaProducerMock).send(eq("tracking.status"), any(TrackingStatusUpdated.class));

        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        Exception exception = assertThrows(RuntimeException.class, () -> service.processDispatchPreparing(testEvent));

        verify(kafkaProducerMock, times(1)).send(eq("tracking.status"), any(TrackingStatusUpdated.class));
        assertThat(exception.getMessage(), equalTo("dispatch preparing producer failure"));
    }

    @Test
    public void process_DispatchedSuccess()  throws Exception {
        when(kafkaProducerMock.send(anyString(), any(TrackingStatusUpdated.class))).thenReturn(mock(CompletableFuture.class));

        DispatchCompleted testEvent = TestEventData.buildDispatchCompletedEvent(UUID.randomUUID(), LocalDate.now().toString());
        service.processDispatched(testEvent);

        verify(kafkaProducerMock, times(1)).send(eq("tracking.status"), any(TrackingStatusUpdated.class));
    }

    @Test
    public void process_DispatchedProducerThrowsException() {
        doThrow(new RuntimeException("dispatched producer failure")).when(kafkaProducerMock).send(eq("tracking.status"), any(TrackingStatusUpdated.class));

        DispatchCompleted testEvent = TestEventData.buildDispatchCompletedEvent(UUID.randomUUID(), LocalDate.now().toString());
        Exception exception = assertThrows(RuntimeException.class, () -> service.processDispatched(testEvent));

        verify(kafkaProducerMock, times(1)).send(eq("tracking.status"), any(TrackingStatusUpdated.class));
        assertThat(exception.getMessage(), equalTo("dispatched producer failure"));
    }

}
