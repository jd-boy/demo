package org.jd.demo.io.reactor.multi;

import org.jd.demo.io.reactor.AbstractDispacther;
import org.jd.demo.io.reactor.SocketHandler;
import org.jd.demo.io.reactor.SocketHandlerImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 子调度器
 */
@Slf4j
public class SubDispacther extends AbstractDispacther {

    private volatile boolean registering = false;

    private final Executor registerExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void run() {
        System.out.printf("%s start running\n", getName());
        while (!Thread.interrupted()) {
            try {
                if (registering) {
                    continue;
                }
                if (selector.select() <= 0) {
                    while (registering);
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
        SocketHandler socketHandler = (SocketHandler) selectionKey.attachment();
        if (selectionKey.isReadable()) {
            socketHandler.read();
        } else if (selectionKey.isWritable()) {
            socketHandler.write();
        }
    }

    public void addSocketChannel(SocketChannel socketChannel) throws IOException {
        registerExecutor.execute(() -> {
            try {
                registering = true;
                selector.wakeup();
                socketChannel.configureBlocking(false);
                SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                selectionKey.attach(new SocketHandlerImpl(selectionKey));
                registering = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
