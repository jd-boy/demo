package org.jd.demo.io.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 抽象调度器
 */
@Slf4j
public abstract class AbstractDispacther implements Runnable {

    protected final Selector selector;

    protected final ServerSocketChannel serverSocketChannel;

    private String name;

    private final AtomicInteger count = new AtomicInteger();

    public AbstractDispacther() {
        try {
            this.selector = Selector.open();
            this.serverSocketChannel = null;
        } catch (IOException e) {
            throw new RuntimeException("Dispacther initialize fail", e);
        }
    }

    public AbstractDispacther(Selector selector) {
        this.selector = selector;
        this.serverSocketChannel = null;
    }

    public AbstractDispacther(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException("Dispacther initialize fail", e);
        }
    }

    public AbstractDispacther(Selector selector, ServerSocketChannel serverSocketChannel, String name) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        System.out.printf("%s start running\n", getName());
        while (!Thread.interrupted()) {
            try {
                selector.select();
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

    protected abstract void dispatch(SelectionKey selectionKey) throws Exception;

    public void setName(String name) {
        if (this.name != null) {
            return;
        } else if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            this.name = "Dispacther-" + count.getAndIncrement();
        }
    }

    protected String getName() {
        if (name == null) {
            setName(null);
        }
        return name;
    }

}
