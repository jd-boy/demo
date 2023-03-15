package org.jd.demo.redis;

import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.dynamic.RedisCommandFactory;
import io.lettuce.core.dynamic.batch.CommandBatching;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RedisTest {

    private static RedisClusterClient client = RedisClusterClient.create("redis://localhost:7005");

//    private static RedisClient az5Client = RedisClient.create("redis://localhost:8001");
//    private static RedisClient az7Client = RedisClient.create("redis://localhost:8002");


    public static void main(String[] args) throws Exception {
        var res = client.connect().sync().mget("jij", "JIInf222");
        for (KeyValue<String, String> re : res) {
            System.out.printf("key=%s  value=%s\n", re.getKey(), re.getValue());
        }
    }

}
