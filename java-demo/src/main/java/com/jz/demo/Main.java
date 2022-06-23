package com.jz.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import lombok.SneakyThrows;

public class Main {

    private static ExecutorService executorService = new ThreadPoolExecutor(2, 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));

//    private static CompletionService<Object> completionService = new ExecutorCompletionService<>();

    @SneakyThrows
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>() {{
            put("1", "a.b");
        }};
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(map));
    }

}
