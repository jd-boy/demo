package com.jz.demo.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private List<String> bootstrapServers;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        factory.setConcurrency(1);
        factory.setBatchListener(true);
        ContainerProperties containerProperties = factory.getContainerProperties();
        containerProperties.setAckMode(AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    private ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        // kafka 地址
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 是否自动提交
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 允许自动创建topic
        configs.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, true);
        // 自动提交间隔
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        // 消费者与coordinator之间session 超时时间，即在该时间内coordinator未收到consumer的任何消息，就会认为该consumer挂了
        configs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        // 向 group coordinator 发送心跳的间隔
        configs.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);
        // key 序列化
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.StringDeserializer");
        // value 序列化
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.StringDeserializer");
        // 消费者组id
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-test");
        // 自动设置 offset
//        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, true);
        // 每次 poll 记录数量
        configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 20);
        // poll 间隔时间
        configs.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1000);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

}
