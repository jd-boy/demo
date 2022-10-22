package org.jd.demo.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class CustomizeEventHandler implements EventHandler<Message>, WorkHandler<Message> {

    @Override
    public void onEvent(Message event, long sequence, boolean endOfBatch) throws Exception {
        handler(event);
    }

    @Override
    public void onEvent(Message event) throws Exception {
        handler(event);
    }

    private void handler(Message message) {
        try {
            Thread.sleep(2);
            System.out.println("messageId：" + message.id + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
