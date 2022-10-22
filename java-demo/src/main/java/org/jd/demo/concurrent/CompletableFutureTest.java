package org.jd.demo.concurrent;

import java.util.concurrent.*;

public class CompletableFutureTest {

    private static final ExecutorService executor = new ThreadPoolExecutor(2, 2,
            1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));

    public static void main(String[] args) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("jijij");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenAcceptAsync(unused -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("完成");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).join();
        }

    }
