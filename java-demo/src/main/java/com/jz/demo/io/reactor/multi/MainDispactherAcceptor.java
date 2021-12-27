package com.jz.demo.io.reactor.multi;

import com.jz.demo.io.reactor.AbstractDispacther;
import com.jz.demo.io.reactor.Acceptor;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class MainDispactherAcceptor implements Acceptor {

    private static final int MIN_DISPACTHER_NUM = 1;

    private final SubDispacther[] dispacthers;

    private final int dispactherNum;

    private final AtomicLong acceptCount = new AtomicLong();

    private final Executor executor;

    public MainDispactherAcceptor(int dispactherNum) {
        if (dispactherNum < MIN_DISPACTHER_NUM) {
            this.dispactherNum = MIN_DISPACTHER_NUM;
        } else {
            this.dispactherNum = dispactherNum;
        }
        this.dispacthers = new SubDispacther[this.dispactherNum];
        this.executor = Executors.newFixedThreadPool(this.dispactherNum);
        initSubDispacther();
    }

    private void initSubDispacther() {
        for (int i = 0; i < dispactherNum; i++) {
            dispacthers[i] = new SubDispacther();
            dispacthers[i].setName("SubDispacther-" + i);
            executor.execute(dispacthers[i]);
        }
    }

    @Override
    public void accept(SelectionKey selectionKey) throws Exception {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel acceptSocketChannel = serverSocketChannel.accept();
        System.out.println("Main dispacther accept event");
        int next = (int) (acceptCount.getAndIncrement() % dispactherNum);
        dispacthers[next].addSocketChannel(acceptSocketChannel);
    }

}
