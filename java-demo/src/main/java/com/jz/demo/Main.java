package com.jz.demo;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    private static ExecutorService executorService = new ThreadPoolExecutor(2, 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));

//    private static CompletionService<Object> completionService = new ExecutorCompletionService<>();

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Thread> a = new AtomicReference<>();
        Future task = executorService.submit(() -> {
            a.set(Thread.currentThread());
            System.out.println("开始");
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("-----");
            }
            System.out.println("end");
        });
        Thread.sleep(1000);
        System.out.println("start cancel");
    }

}
