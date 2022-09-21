package com.jz.demo.redis;

import io.lettuce.core.Consumer;
import io.lettuce.core.RedisClient;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.XReadArgs.StreamOffset;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.NodeSelection;
import io.lettuce.core.dynamic.RedisCommandFactory;
import io.lettuce.core.dynamic.batch.CommandBatching;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RedisTest {

    private static RedisClusterClient client = RedisClusterClient.create("redis://localhost:8001");

    private static RedisClient az5Client = RedisClient.create("redis://localhost:8001");
    private static RedisClient az7Client = RedisClient.create("redis://localhost:8002");


    public static void main(String[] args) throws Exception {
        var az5Comm = az5Client.connect().sync();
        var az7Comm = az7Client.connect().sync();
        var az5Keys = az5Comm.keys("THIRD_*").subList(0, 3000);

        System.out.println("Key数量：" + az5Keys.size());
        int eqCount = 0;
        int commonCount = 0;
        List<AA> list = new ArrayList<>();
        for (String key : az5Keys) {
            var az5Map = az5Comm.hgetall(key);
            var az7Map = az7Comm.hgetall(key);
            if (az5Map != null && az7Map != null &&
                !az5Map.isEmpty() && !az7Map.isEmpty()) {
                commonCount++;
            } else {
                continue;
            }
            if (az7Map.size() != az5Map.size()) {
                list.add(new AA(az5Map, az7Map));
                continue;
            }
            boolean a = true;
            for (Entry<String, String> en : az5Map.entrySet()) {
                if ("timestamp".equals(en.getKey())) {
                    continue;
                }
                if (!az7Map.get(en.getKey()).equals(en.getValue())) {
                    a = false;
                    break;
                }
            }
            if (a) {
                eqCount++;
            } else {
                list.add(new AA(az5Map, az7Map));
            }

        }
        System.out.println("比例 " + eqCount + " / " + commonCount);
        for (AA aa : list) {
            System.out.println(aa);
        }
    }


    static class AA {

        Map<String, String> a;
        Map<String, String> b;

        AA(Map<String, String> a, Map<String, String> b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "start-------------------\n" +
                a.toString() + "\n" +
                b.toString() + "\n" +
                "end-------------------";
        }
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
