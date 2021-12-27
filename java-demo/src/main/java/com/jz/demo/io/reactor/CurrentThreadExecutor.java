package com.jz.demo.io.reactor;

import java.util.concurrent.Executor;

/**
 * 当前线程执行的执行器
 */
public class CurrentThreadExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        if (command != null) {
            command.run();
        }
    }

}
