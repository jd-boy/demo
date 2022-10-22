package org.jd.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static org.jd.demo.kafka.KafkaConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void templateSend() {
        kafkaTemplate.send(TEST_TOPIC, "template test message");
    }

}
