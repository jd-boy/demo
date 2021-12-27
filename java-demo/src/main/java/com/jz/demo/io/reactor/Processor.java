package com.jz.demo.io.reactor;

import com.jz.demo.io.IOUtils;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Processor implements Runnable {

    private final SelectionKey selectionKey;

    private final byte[] data;

    private final int num;

    public Processor(SelectionKey selectionKey, byte[] data, int num) {
        this.selectionKey = selectionKey;
        this.data = data;
        this.num = num;
    }

    @Override
    public void run() {
        String content = new String(data, StandardCharsets.UTF_8);
//        System.out.printf("线程：%s num：%d 接收到：\n%s%n", Thread.currentThread().getName(), num, content);
        IOUtils.doSomething();
        selectionKey.attach(IOUtils.buildResp("{'b': '响应：HelloWord-" + num + "'}").getBytes(StandardCharsets.UTF_8));
        int ops = selectionKey.interestOps();
        // 添加对写事件监听
        selectionKey.interestOps(ops | SelectionKey.OP_WRITE);
        selectionKey.selector().wakeup();
    }

}
