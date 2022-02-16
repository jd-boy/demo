package com.jz.demo.kafka.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaEventHandler implements EventHandler<KafaEvent>, WorkHandler<KafaEvent> {

    private static KafkaProducer<String, String> producer;

    static {
        Map<String, Object> config = new HashMap<>();

        // 连接地址
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.18.20.14:19092");
        // ACK
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        // 相应超时.
        config.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 5000);
        // 缓冲区大小. (发送给服务器的)
        config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024 * 1024 * 10);
        // 每次发送的数据大小
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 16 * 1024);
        // 生产者生产过快，来不及发送到server最大阻塞时间，超过该时间将会抛出异常
        config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 20 * 1024);
        // 不重试,有些非幂等性可以.
        config.put(ProducerConfig.RETRIES_CONFIG, 0);
        // snappy 压缩..
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        // 序列化
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        producer = new KafkaProducer<>(config);
    }

    @Override
    public void onEvent(KafaEvent event, long sequence, boolean endOfBatch) throws Exception {
        handler(event);
    }

    @Override
    public void onEvent(KafaEvent event) throws Exception {
        handler(event);
    }

    private void handler(KafaEvent kafaEvent) {
        System.out.println("send " + kafaEvent.getMessage());
        producer.send(new ProducerRecord<>("test1", "test-key", kafaEvent.getMessage()));
    }

}
