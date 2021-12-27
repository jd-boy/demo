package com.jz.demo.io.reactor.multi;

import com.jz.demo.io.reactor.AbstractDispacther;
import com.jz.demo.io.reactor.SocketHandler;
import com.jz.demo.io.reactor.SocketHandlerImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * 子调度器
 */
@Slf4j
public class SubDispacther extends AbstractDispacther {

    private final SocketHandler socketHandler = new SocketHandlerImpl();

    private final Queue<SocketChannel> socketChannelCache = new ConcurrentLinkedQueue<>();
//    private final Executor acceptWorker = Executors.newSingleThreadExecutor();

    @Override
    public void run() {
        System.out.printf("%s start running\n", getName());
        while (!Thread.interrupted()) {
            try {
                int num = selector.select();
                if (num <= 0) {
                    SocketChannel socketChannel = null;
                    while ((socketChannel = socketChannelCache.poll()) != null) {
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    dispatch(selectionKey);
                    iterator.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void dispatch(SelectionKey selectionKey) throws Exception {
        if (selectionKey.isReadable()) {
            socketHandler.read(selectionKey);
        } else if (selectionKey.isWritable()) {
            socketHandler.write(selectionKey);
        }
    }

    public void addSocketChannel(SocketChannel socketChannel) throws IOException {
        socketChannelCache.add(socketChannel);
        selector.wakeup();
//        acceptWorker.execute(() -> {
//            try {
//                socketChannel.configureBlocking(false);
//                socketChannel.register(selector, SelectionKey.OP_READ);
//                selector.wakeup();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        System.out.println("SubDispacther add SocketChannel");
    }

}
