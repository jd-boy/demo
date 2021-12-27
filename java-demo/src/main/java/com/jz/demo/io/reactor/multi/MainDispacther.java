package com.jz.demo.io.reactor.multi;

import com.jz.demo.io.reactor.Acceptor;
import com.jz.demo.io.reactor.AbstractDispacther;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * 主调度器，只接收连接事件
 */
@Slf4j
public class MainDispacther extends AbstractDispacther {

    private final Acceptor acceptor;

    public MainDispacther(int port) {
        this(port, 4);
    }

    public MainDispacther(int port, int subDispactherNum) {
        super(port);
        this.acceptor = new MainDispactherAcceptor(subDispactherNum);
    }

    @Override
    protected void dispatch(SelectionKey selectionKey) throws Exception {
        if (selectionKey.isAcceptable()) {
            acceptor.accept(selectionKey);
        }
    }

}
