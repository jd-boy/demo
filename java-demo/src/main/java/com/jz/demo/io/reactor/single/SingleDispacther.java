package com.jz.demo.io.reactor.single;

import com.jz.demo.io.reactor.Acceptor;
import com.jz.demo.io.reactor.AbstractDispacther;
import com.jz.demo.io.reactor.SocketHandler;
import com.jz.demo.io.reactor.SocketHandlerImpl;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SelectionKey;
import java.util.concurrent.Executors;

/**
 * 单Reactor模式调度器
 */
@Slf4j
public class SingleDispacther extends AbstractDispacther {

    private final Acceptor acceptor;

    private final SocketHandler socketHandler;

    public SingleDispacther(int port) {
        this(port, 0);
    }

    public SingleDispacther(int port, int processorNum) {
        super(port);
        acceptor = new SingleReactorAcceptor(selector);
        if (processorNum > 0) {
            socketHandler = new SocketHandlerImpl(Executors.newFixedThreadPool(processorNum));
        } else {
            socketHandler = new SocketHandlerImpl();
        }
    }

    @Override
    protected void dispatch(SelectionKey selectionKey) throws Exception {
        if (selectionKey.isAcceptable()) {
            System.out.println("acceptable even");
            acceptor.accept(selectionKey);
        } else if (selectionKey.isReadable()) {
            socketHandler.read(selectionKey);
        } else if (selectionKey.isWritable()) {
            socketHandler.write(selectionKey);
        }
    }

}
