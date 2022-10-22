package org.jd.demo.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

public class DisruptorMain {

    public static void main(String[] args) throws InterruptedException {
        CustomizeEventFactory customizeEventFactory = new CustomizeEventFactory();
        int ringBufferSize = 1024;
        Disruptor<Message> disruptor = new Disruptor<>(
                customizeEventFactory,
                ringBufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new YieldingWaitStrategy()
        );
        disruptor.handleEventsWith(new CustomizeEventHandler());
        disruptor.start();

        RingBuffer<Message> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        long s = System.currentTimeMillis();

        int num = 20;
        for (int i = 0; i < num; i++) {
            producer.publish(i);
        }
        System.out.println("Disruptor 事件入队总耗时：" + (System.currentTimeMillis() - s) + " ms");
        Thread.sleep(5000);
        disruptor.shutdown();
        Thread.sleep(5000);
        System.out.println("Disruptor 执行完毕任务总耗时：" + (System.currentTimeMillis() - s - 10000) + " ms");
    }

}
