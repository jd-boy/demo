package com.jz.demo.disruptor;

import com.lmax.disruptor.RingBuffer;

public class Producer {

    private final RingBuffer<Message> ringBuffer;

    public Producer(RingBuffer<Message> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(int data){
        long sequence = ringBuffer.next();
        try {
            Message message = ringBuffer.get(sequence);
            message.id = data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
