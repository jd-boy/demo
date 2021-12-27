package com.jz.demo.io.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketHandlerImpl implements SocketHandler {

    private final Executor executor;

    private final AtomicInteger count = new AtomicInteger();

    public SocketHandlerImpl() {
        this(new CurrentThreadExecutor());
    }

    public SocketHandlerImpl(Executor executor) {
        if (executor == null) {
            throw new IllegalArgumentException("Executor cannot be null");
        }
        this.executor = executor;
    }

    @Override
    public void read(SelectionKey selectionKey) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            int len = socketChannel.read(buffer);
            if (len > 0) {
                buffer.flip();
                executor.execute(new Processor(selectionKey, buffer.array(), count.getAndIncrement()));
                buffer.clear();
            } else if (len < 0) {
                System.out.println("关闭连接：" + socketChannel);
                selectionKey.cancel();
                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(SelectionKey selectionKey) throws IOException {
        SocketChannel writeSocketChannel = (SocketChannel) selectionKey.channel();
        byte[] bytes = (byte[]) selectionKey.attachment();
//        System.out.println("写出数据");
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        writeSocketChannel.write(ByteBuffer.wrap(bytes));
        int ops = selectionKey.interestOps();
        // 取消监听写事件
        selectionKey.interestOps(ops ^ SelectionKey.OP_WRITE);
    }

}
