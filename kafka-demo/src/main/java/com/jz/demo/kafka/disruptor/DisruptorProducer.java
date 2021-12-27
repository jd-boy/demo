package com.jz.demo.kafka.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DisruptorProducer {

    private final Disruptor<KafaEvent> disruptor;

    private final RingBuffer<KafaEvent> ringBuffer;

    public DisruptorProducer(int ringBufferSize) {
        this.disruptor = new Disruptor<>(
                new KafkaEventFactory(),
                ringBufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new SleepingWaitStrategy(200, TimeUnit.SECONDS.toNanos(1))
        );
        disruptor.handleEventsWith(new KafkaEventHandler());
        this.ringBuffer = disruptor.getRingBuffer();
    }

    public void start() {
        disruptor.start();
    }

    public void shoutdown() {
        disruptor.shutdown();
    }

    public void publish(String message){
        long sequence = ringBuffer.next();
        try {
            KafaEvent kafaEvent = ringBuffer.get(sequence);
            kafaEvent.setMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
