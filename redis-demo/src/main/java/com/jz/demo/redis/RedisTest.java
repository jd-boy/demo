package com.jz.demo.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.XReadArgs.StreamOffset;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.NodeSelection;
import io.lettuce.core.dynamic.RedisCommandFactory;
import io.lettuce.core.dynamic.batch.CommandBatching;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisTest {

//    private static RedisClusterClient client = RedisClusterClient.create("redis://localhost:6379");

    private static RedisClient client = RedisClient.create("redis://localhost:6379");


    public static void main(String[] args) throws Exception {
        var comm = client.connect().sync();
        var s = comm.xgroupCreate(
            StreamOffset.from("redisStreamKey", "0-0"), "consumerGroupName");
        System.out.println(s);
//        for (int i = 0; i < 1000; i++) {
////            comm.set("FREQ_STREAM_" + i, map.toString());
//            comm.del("FREQ_STREAM_" + i);
//        }
    }

    private static void batchCommandTest() throws Exception {
        RedisCommandFactory factory = new RedisCommandFactory(client.connect());
        BatchCommands batchCommands = factory.getCommands(BatchCommands.class);
        batchCommands.set("{jz:}qj", "1");
        batchCommands.set("{jz:}qqj", "12");
        batchCommands.set("{jz:}qqqj", "123");

        batchCommands.get("{jz:}qj").thenAcceptAsync(value -> System.out.println(value));
        batchCommands.get("{jz:}qqj", CommandBatching.queue()).thenAcceptAsync(value -> System.out.println(value));
        batchCommands.get("{jz:}qqqj", CommandBatching.flush()).thenAcceptAsync(value -> System.out.println(value));
        System.out.println("------------------");
        Thread.sleep(2000);


        batchCommands.set("{jz:}qj1", "1w");
        batchCommands.set("{jz:}qqj1", "12w");
        batchCommands.set("{jz:}qqqj1", "123w");

        batchCommands.get("{jz:}qj1").thenAcceptAsync(value -> System.out.println(value));
        batchCommands.get("{jz:}qqj1").thenAcceptAsync(value -> System.out.println(value));
        batchCommands.get("{jz:}qqqj1").thenAcceptAsync(value -> System.out.println(value));
        batchCommands.flush();
        System.out.println("------------------");
        Thread.sleep(2000);
    }

}
