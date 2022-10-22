package org.jd.demo.redis;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/redis/")
@RequiredArgsConstructor
public class RedisController {

    private final RedisTemplate redisTemplate;

    private final RedisClusterClient redisClient;

    @GetMapping(value = "test")
    public void test() throws ExecutionException, InterruptedException {
        RedisAdvancedClusterAsyncCommands<String, String> commands = redisClient.connect().async();
        RedisFuture<String> future = commands.set("jk", "iji");
        future.thenAcceptAsync(value -> {
            try {
                System.out.println(commands.get("jk").get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("结束");
    }

}
