package com.jz.demo.redis;

import io.lettuce.core.cluster.RedisClusterClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean
    RedisClusterClient redisClient() {
        return RedisClusterClient.create("redis://localhost:7000");
    }

}
