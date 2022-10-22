package org.jd.demo.io.reactor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger sequence = new AtomicInteger(1);

    private final String prefix;

    public NamedThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(prefix + "-" + sequence.getAndIncrement());
        return thread;
    }

}
