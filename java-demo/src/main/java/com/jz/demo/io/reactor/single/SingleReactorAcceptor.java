package com.jz.demo.io.reactor.single;

import com.jz.demo.io.reactor.Acceptor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 单Reactor模式连接接收器
 */
public class SingleReactorAcceptor implements Acceptor {

    private final Selector selector;

    public SingleReactorAcceptor(Selector selector) {
        this.selector = selector;
    }

    public void accept(SelectionKey selectionKey) throws Exception {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel acceptSocketChannel = serverSocketChannel.accept();
        acceptSocketChannel.configureBlocking(false);
        // 注册读事件
        acceptSocketChannel.register(selector, SelectionKey.OP_READ);
    }

}
