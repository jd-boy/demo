package com.jz.demo.disruptor;

import com.lmax.disruptor.EventFactory;

public class CustomizeEventFactory implements EventFactory<Message> {
    @Override
    public Message newInstance() {
        return new Message();
    }
}
