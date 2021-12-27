package com.jz.demo.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    private static final Semaphore semaphore = new Semaphore(4);

    private static final Executor executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            executor.execute(new SemaphoreTestRunnable());
        }
    }

    static class SemaphoreTestRunnable implements Runnable {
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("处理中");
                Thread.sleep(10000);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
