package com.jz.demo.kafka.disruptor;

import com.lmax.disruptor.EventFactory;

public class KafkaEventFactory implements EventFactory<KafaEvent> {
    @Override
    public KafaEvent newInstance() {
        return new KafaEvent();
    }
}
