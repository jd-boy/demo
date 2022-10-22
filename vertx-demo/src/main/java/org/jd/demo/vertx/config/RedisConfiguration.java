package org.jd.demo.vertx.config;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther jd
 */
@Slf4j
@Setter
@Configuration
@ConfigurationProperties("vertx.redis")
public class RedisConfiguration {

  private List<String> uris = new ArrayList<>();

  private String password;

  private RedisClientType clientType;

  private int maxPoolWaiting = 24;

  private int maxPoolSize = 6;

  @Bean
  Redis redisClient(Vertx vertx) {
    Redis redis = Redis.createClient(
        vertx,
        redisOptions()
    );
//    tryConnect(redis, 5);
    return redis;
  }

  private RedisOptions redisOptions() {
    RedisOptions redisOptions = new RedisOptions();
    uris.forEach(redisOptions::addConnectionString);
    return redisOptions.setMaxPoolWaiting(maxPoolWaiting)
        .setNetClientOptions(new NetClientOptions().setConnectTimeout(10))
        .setPassword(password)
        .setMaxPoolSize(maxPoolSize);
  }

  private void tryConnect(Redis redis, int trytimes) {
    redis.connect()
        .onFailure(e -> {
          if (trytimes <= 0) {
            log.warn("Redis 连接异常");
            throw new RuntimeException(e);
          }
          tryConnect(redis, trytimes - 1);
        })
        .onSuccess(connect -> {
          log.info("Redis 连接成功");
          connect.close();
        });
  }

}
