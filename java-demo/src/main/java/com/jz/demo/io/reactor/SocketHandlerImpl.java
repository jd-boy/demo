package com.jz.demo.io.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketHandlerImpl implements SocketHandler {

    private static final int DEFAULT_BYTE_BUFFER_CAPACITY = 512;

    private final Executor executor;

    private final ByteBuffer byteBuffer;

    private final SelectionKey selectionKey;

    public SocketHandlerImpl(SelectionKey selectionKey) {
        this(selectionKey, new CurrentThreadExecutor(), DEFAULT_BYTE_BUFFER_CAPACITY);
    }

    public SocketHandlerImpl(SelectionKey selectionKey, Executor executor) {
        this(selectionKey, executor, DEFAULT_BYTE_BUFFER_CAPACITY);
    }

    public SocketHandlerImpl(SelectionKey selectionKey, Executor executor, int capacity) {
        if (selectionKey == null) {
            throw new IllegalArgumentException("SelectionKey cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Executor cannot be null");
        }
        this.selectionKey = selectionKey;
        this.executor = executor;
        this.byteBuffer = ByteBuffer.allocate(capacity);
    }

    @Override
    public void read() {
        try {
            byteBuffer.clear();
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            int len = socketChannel.read(byteBuffer);
            //TODO 一次读取完毕
            System.out.println(len);
            if (len > 0) {
                byteBuffer.flip();
                process(byteBuffer.array());
            } else if (len < 0) {
                // 小于0时，客户端关闭input
                selectionKey.cancel();
                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(byte[] bytes) {
        CompletableFuture.supplyAsync(new DefaultProcessor(bytes), executor)
                .thenAccept(result -> {
                    byteBuffer.put(result);
                    // 添加对写事件监听
                    int ops = selectionKey.interestOps();
                    selectionKey.interestOps(ops | SelectionKey.OP_WRITE);
                    selectionKey.selector().wakeup();
                });
    }

    @Override
    public void write() throws IOException {
        SocketChannel writeSocketChannel = (SocketChannel) selectionKey.channel();
        byteBuffer.rewind();
        writeSocketChannel.write(byteBuffer);
        // 取消监听写事件
        int ops = selectionKey.interestOps();
        selectionKey.interestOps(ops ^ SelectionKey.OP_WRITE);
    }

}
