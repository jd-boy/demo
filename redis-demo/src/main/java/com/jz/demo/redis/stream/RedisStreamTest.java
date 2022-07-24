package com.jz.demo.redis.stream;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Auther jd
 */
public class RedisStreamTest {

  private final static RedisCommands<String, String> syncComm = RedisClient.create("redis://localhost:6379")
      .connect().sync();

  private final static String redisKey = "TestStream";
  private final static String fieldKey = "TestField";

  private final static RedisStreamProducer<RedisStreamDTO> producer = new RedisStreamProducer<RedisStreamDTO>(
      redisKey, fieldKey,2, 100,
      10, new LinkedBlockingQueue<RedisStreamDTO>(), syncComm);

  private final static RedisStreamConsumerGroup consumerGroup = new RedisStreamConsumerGroup(
      redisKey, "TestConsumerGroup", 2, true,
      message -> {
        System.out.println("消费消息：" + message.getBody().get(fieldKey));
      }
  );

  public static void main(String[] args) {
    producer.strat();
    consumerGroup.start();


    new Thread(() -> {
      while (true) {
        producer.send(new RedisStreamDTO(1, "自己解决"));
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

}
