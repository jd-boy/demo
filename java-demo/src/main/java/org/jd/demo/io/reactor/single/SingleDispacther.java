package org.jd.demo.io.reactor.single;

import com.jz.demo.io.reactor.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SelectionKey;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jd.demo.io.reactor.AbstractDispacther;
import org.jd.demo.io.reactor.Acceptor;
import org.jd.demo.io.reactor.CurrentThreadExecutor;
import org.jd.demo.io.reactor.NamedThreadFactory;
import org.jd.demo.io.reactor.SocketHandler;

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
