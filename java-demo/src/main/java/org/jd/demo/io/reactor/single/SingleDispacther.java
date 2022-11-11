package org.jd.demo.io.reactor.single;

import lombok.extern.slf4j.Slf4j;
import org.jd.demo.io.reactor.*;

import java.nio.channels.SelectionKey;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 单Reactor模式调度器
 */
@Slf4j
public class SingleDispacther extends AbstractDispacther {

    private final Acceptor acceptor;

    private final Executor workExecutor;

    public SingleDispacther(int port) {
        this(port, 0);
    }

    public SingleDispacther(int port, int processorNum) {
        super(port);
        if (processorNum > 1) {
            this.workExecutor = Executors.newFixedThreadPool(processorNum, new NamedThreadFactory("SingleDispacther-thread-pool"));
        } else if (processorNum == 1) {
            this.workExecutor = Executors.newSingleThreadExecutor(new NamedThreadFactory("SingleDispacther-thread-pool"));
        } else {
            this.workExecutor = new CurrentThreadExecutor();
        }
        acceptor = new SingleReactorAcceptor(selector, this.workExecutor);
    }

    @Override
    protected void dispatch(SelectionKey selectionKey) throws Exception {
        SocketHandler socketHandler = (SocketHandler) selectionKey.attachment();
        if (selectionKey.isAcceptable()) {
//            System.out.println("SingleDispacther accept");
            acceptor.accept(selectionKey);
        } else if (selectionKey.isReadable()) {
//            System.out.println("SingleDispacther start read");
            socketHandler.read();
//            System.out.println("SingleDispacther end read");
        } else if (selectionKey.isWritable()) {
//            System.out.println("SingleDispacther start write");
            socketHandler.write();
//            System.out.println("SingleDispacther end write");
        }
    }

}
