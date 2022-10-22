package org.jd.demo.kafka.disruptor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class KafkaDisruptorMain {

    private static final DisruptorProducer DISRUPTOR_PRODUCER = new DisruptorProducer(32);

    private static long startTime;

    private static final CyclicBarrier cyclicBarrier =
            new CyclicBarrier(3, () -> startTime = System.currentTimeMillis());

    private static final CountDownLatch countDownLatch = new CountDownLatch(3);

    private static int num = 100;

    public static void main(String[] args) throws InterruptedException {
        DISRUPTOR_PRODUCER.start();
        new KafkaEventThread("事件a").start();
        new KafkaEventThread("事件b").start();
        new KafkaEventThread("事件c").start();
        countDownLatch.await();
        log.info("消息入队完毕，耗时：{} ms", System.currentTimeMillis() - startTime);
        DISRUPTOR_PRODUCER.shoutdown();
        Thread.sleep(5000);
        log.info("消息发送完毕总耗时：{} ms", System.currentTimeMillis() - startTime);
    }

    static class KafkaEventThread extends Thread {

        private String prefix;

        public KafkaEventThread(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < num; i++) {
                DISRUPTOR_PRODUCER.publish(prefix + '-' + i);
            }
            countDownLatch.countDown();
        }
    }

}
