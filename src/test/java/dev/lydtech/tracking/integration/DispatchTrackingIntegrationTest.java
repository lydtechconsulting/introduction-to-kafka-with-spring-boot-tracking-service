package dev.lydtech.tracking.integration;

import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.tracking.TrackingConfiguration;
import dev.lydtech.tracking.message.TrackingStatusUpdated;
import dev.lydtech.tracking.util.TestEventData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.UUID.randomUUID;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@SpringBootTest(classes = {TrackingConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
@EmbeddedKafka(controlledShutdown = true)
public class DispatchTrackingIntegrationTest {

    private final static String DISPATCH_TRACKING_TOPIC = "dispatch.tracking";

    private final static String TRACKING_STATUS_TOPIC = "tracking.status";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private KafkaTestListener testListener;

    @Configuration
    static class TestConfig {
        @Bean
        public KafkaTestListener testListener() {
            return new KafkaTestListener();
        }

// Uncomment this to overcome the problem with an untrusted deserialization class for the test consumer
//        @Bean
//        public ConcurrentKafkaListenerContainerFactory<String, DispatchPreparing> kafkaTestListenerContainerFactory(ConsumerFactory<String, DispatchPreparing> consumerTestFactory) {
//            final ConcurrentKafkaListenerContainerFactory<String, DispatchPreparing> factory = new ConcurrentKafkaListenerContainerFactory<>();
//            factory.setConsumerFactory(consumerTestFactory);
//            return factory;
//        }
//
//        @Bean
//        public ConsumerFactory<String, DispatchPreparing> consumerTestFactory(@Value("${kafka.bootstrap-servers}") String bootstrapServers) {
//            final Map<String, Object> config = new HashMap<>();
//            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//            config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//            config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//            config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, TrackingStatusUpdated.class);
//            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//            return new DefaultKafkaConsumerFactory<>(config);
//        }
    }

    public static class KafkaTestListener {
        AtomicInteger trackingStatusCounter = new AtomicInteger(0);

        @KafkaListener(groupId = "kafkaIntegrationTest", topics = TRACKING_STATUS_TOPIC) //, containerFactory = "kafkaTestListenerContainerFactory")
        void receiveTrackingStatus(@Payload TrackingStatusUpdated payload) {
            log.debug("Received TrackingStatus: " + payload);
            trackingStatusCounter.incrementAndGet();
        }

    }

    @BeforeEach
    public void setUp() {
        testListener.trackingStatusCounter.set(0);

        registry.getListenerContainers().stream().forEach(container ->
                ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic()));
    }

    @Test
    public void testDispatchTrackingFlow() throws Exception{
        DispatchPreparing dispatchPreparing = TestEventData.buildDispatchPreparingEvent(randomUUID());
        sendMessage(DISPATCH_TRACKING_TOPIC, dispatchPreparing);

        await().atMost(3, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS)
                .until(testListener.trackingStatusCounter::get, equalTo(1));

    }

    private void sendMessage(String topic, Object data) throws Exception {
        kafkaTemplate.send(MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build()).get();
    }
}

