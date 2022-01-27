package com.jz.demo.vertx.config;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther jd
 */
@Setter
@Configuration
@ConfigurationProperties("vertx.redis")
public class RedisConfiguration {

  private List<String> uris = new ArrayList<>();

  private int maxPoolWaiting = 24;

  private int maxPoolSize = 6;

  @Bean
  Redis redisClient(Vertx vertx) {
    return Redis.createClient(
        vertx,
        redisOptions()
    );
  }

  private RedisOptions redisOptions() {
    RedisOptions redisOptions = new RedisOptions();
    uris.forEach(redisOptions::addConnectionString);
    return redisOptions.setMaxPoolWaiting(maxPoolWaiting)
        .setNetClientOptions(new NetClientOptions().setConnectTimeout(10))
        .setMaxPoolSize(maxPoolSize);
  }

}
