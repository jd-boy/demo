package com.jz.demo.kafka;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = "test")
    public void testTopic(String msg) {
        log.info("topic test, msg:{}", msg);
    }

    @KafkaListener(topics = "test1", containerFactory = "kafkaListenerContainerFactory")
    public void test1Topic(List<ConsumerRecord<String, Object>> records) {
        records.forEach(record -> {
            log.info("topic test1, msg:{}", record.value());
        });
    }
}
