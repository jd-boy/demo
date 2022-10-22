package org.jd.demo.redis.stream;

import io.lettuce.core.RedisClient;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs.StreamOffset;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * @Auther jd
 */
@Log4j2
public class RedisStreamConsumerGroup {

  private final static String CREATE_MSG_FIELD = "createMsg";

  private final String redisStreamKey;

  private final String consumerGroupName;

  private final int redisStreamNum;

  private final boolean autoAck;

  private final List<RedisStreamConsumer> consumers;

  private final Consumer<StreamMessage<String, String>> consumer;

  private RedisCommands<String, String> syncComm;

  public RedisStreamConsumerGroup(String redisStreamKey, String consumerGroupName,
      int redisStreamNum, boolean autoAck, Consumer<StreamMessage<String, String>> consumer) {
    Assert.hasText(redisStreamKey, "RedisStreamKey cannot be empty");
    Assert.hasText(consumerGroupName, "consumerGroupName cannot be empty");
    Assert.isTrue(redisStreamNum > 0, "RedisStreamNum must be greater than 0");
    Assert.notNull(consumer, "Consumer cannot be null");
    this.redisStreamKey = redisStreamKey;
    this.consumerGroupName = consumerGroupName;
    this.redisStreamNum = redisStreamNum;
    this.autoAck = autoAck;
    this.consumer = consumer;
    this.consumers = new ArrayList<>(redisStreamNum);
  }

  public void start() {
    log.info("Start reids stream consumer group.{}", this::toString);
    this.syncComm = RedisClient.create("redis://localhost:6379").connect().sync();
    if (!redisStreamIsExists()) {
      syncComm.xadd(redisStreamKey, CREATE_MSG_FIELD, "模板");
    }
    for (int i = 0; i < redisStreamNum; i++) {
      String redisKey = redisStreamKey + i;
      createStreamConsumerGroup(redisKey);
      RedisStreamConsumer redisStreamConsumer = new RedisStreamConsumer(
          redisKey, consumerGroupName, UUID.randomUUID().toString(), autoAck, consumer);
      consumers.add(redisStreamConsumer);
      redisStreamConsumer.start();
    }
  }

  public void shutdown() {
    log.info("Shutdown reids stream consumer group.{}", this::toString);
    for (RedisStreamConsumer consumer : consumers) {
      consumer.shutdown();
    }
  }

  @SneakyThrows
  private void createStreamConsumerGroup(String redisStreamKey) {
    // 已存在消费者组，不再创建
    if (consumerGroupIsExists(redisStreamKey)) {
      return;
    }
    // 如果创建失败重试3次，防止因为其他消费者已创建或者网络原因导致无法创建
    int retry = 3;
    do {
      try {
        String createResult = syncComm.xgroupCreate(
            StreamOffset.from(redisStreamKey, "0-0"), consumerGroupName);
        if ("OK".equals(createResult)) {
          log.info("Redis Stream Key：{} 创建消费者组：{} 成功", redisStreamKey, consumerGroupName);
          return;
        }
      } catch (Exception e) { }
      Thread.sleep(5);
      retry--;
    } while (retry > 0);
    throw new RuntimeException(String.format("Redis Stream Key：%s 创建消费者组：%s 失败", redisStreamKey, consumerGroupName));
  }

  private boolean redisStreamIsExists() {
    Long result = syncComm.exists(redisStreamKey);
    return result != null && result > 0L;
  }

  private boolean consumerGroupIsExists(String redisStreamKey) {
    List<Object> result = syncComm.xinfoGroups(redisStreamKey);
    if (CollectionUtils.isEmpty(result)) {
      return false;
    }
    for (Object o : result) {
      List<String> consumerInfo = (List<String>) o;
      if (!CollectionUtils.isEmpty(consumerInfo) &&
          consumerInfo.stream().anyMatch(str -> Objects.equals(consumerGroupName, str))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "ConsumerGroupName：" + consumerGroupName +
        "RedisStreamNum：" + redisStreamNum;
  }
}
