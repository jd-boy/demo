package com.jz.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Test {

    private static final Logger logger = LogManager.getLogger(Test.class);

    private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        String str = "1234";
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));

        System.out.println(byteBuffer.position() == byteBuffer.capacity());
    }
}
