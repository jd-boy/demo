package org.jd.demo;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test {

    private static final Logger logger = LogManager.getLogger(Test.class);

    private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":   " + new Date());
        }, 1, 1, TimeUnit.SECONDS);

    }
}
