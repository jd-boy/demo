package org.jd.demo.io.reactor.single;

import org.jd.demo.io.reactor.Acceptor;
import org.jd.demo.io.reactor.SocketHandlerImpl;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;

/**
 * 单Reactor模式连接接收器
 */
public class SingleReactorAcceptor implements Acceptor {

    private final Selector selector;

    private final Executor workExecutor;

    public SingleReactorAcceptor(Selector selector, Executor workExecutor) {
        if (workExecutor == null) {
            throw new IllegalArgumentException("Executor cannot be null");
        }
        this.selector = selector;
        this.workExecutor = workExecutor;
    }

    public void accept(SelectionKey selectionKey) throws Exception {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel acceptSocketChannel = serverSocketChannel.accept();
        acceptSocketChannel.configureBlocking(false);
        // 注册读事件
        SelectionKey acceptSelectionKey = acceptSocketChannel.register(selector, SelectionKey.OP_READ);
        acceptSelectionKey.attach(new SocketHandlerImpl(acceptSelectionKey, workExecutor, 512));
    }

}
