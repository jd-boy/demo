package com.jz.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.channels.SelectionKey;
import java.util.concurrent.*;

public class Test {

    private static final Logger logger = LogManager.getLogger(Test.class);

    private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
//        ArrayBlockingQueue
        System.out.println(8 >> 1);
    }
}
