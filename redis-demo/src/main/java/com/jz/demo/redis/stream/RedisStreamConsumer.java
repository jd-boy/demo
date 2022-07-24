package com.jz.demo.redis.stream;

import io.lettuce.core.Consumer;
import io.lettuce.core.Limit;
import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.XReadArgs.StreamOffset;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.models.stream.PendingMessage;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Auther jd
 */
@Log4j2
public class RedisStreamConsumer extends Thread {

  private final RedisCommands<String, String> syncComm;

  private final String redisStreamKey;

  private final String consumerGroupName;

  private final String consumerName;

  private final boolean autoAck;

  private final java.util.function.Consumer<StreamMessage<String, String>> consumer;


  public RedisStreamConsumer(String redisStreamKey, String consumerGroupName, String consumerName,
      boolean autoAck, java.util.function.Consumer<StreamMessage<String, String>> consumer) {
    Assert.hasText(redisStreamKey, "RedisStreamKey cannot be empty");
    Assert.hasText(consumerGroupName, "ConsumerGroupName cannot be empty");
    Assert.hasText(consumerName, "ConsumerName cannot be empty");
    this.redisStreamKey = redisStreamKey;
    this.consumerGroupName = consumerGroupName;
    this.consumerName = consumerName;
    this.autoAck = autoAck;
    this.consumer = consumer;
    this.syncComm = RedisClient.create("redis://localhost:6379").connect().sync();
  }


  @Override
  public void run() {
    Set<String> ackMsgids = autoAck ? new HashSet<>() : Collections.emptySet();
    do {
      try {
        var msgList = pullMessage();
        if (CollectionUtils.isEmpty(msgList)) {
          continue;
        }
        for (StreamMessage<String, String> message : msgList) {
          String msgId = null;
          if (Objects.isNull(message) || !StringUtils.hasText(msgId = message.getId())) {
            continue;
          }
          consumer.accept(message);
          if (autoAck) {
            ackMsgids.add(msgId);
          }
        }
        if (autoAck && !CollectionUtils.isEmpty(ackMsgids)) {
          syncComm.xack(redisStreamKey, consumerGroupName, ackMsgids.toArray(new String[0]));
          ackMsgids.clear();
        }
      } catch (Exception e) {
        log.error("{} 消息消费异常：{}", this::toString, e::getMessage);
        e.printStackTrace();
      }
    } while (true);
  }

  @Override
  public void start() {
    log.info("Start reids stream consumer group success.{}", this::toString);
    super.start();
  }

  private List<StreamMessage<String, String>> pullMessage() {
    try {
      // 先检查是否存在死信消息
      List<PendingMessage> allPendingMessages = syncComm.xpending(redisStreamKey, consumerGroupName,
              Range.create("-", "+"), Limit.from(10));
      if (!CollectionUtils.isEmpty(allPendingMessages)) {
        // 消息在队列中penging的次数，预设到达100次时，认为时死信，则进行删除
        List<PendingMessage> deadMessages = allPendingMessages.stream()
            .filter(e -> e.getRedeliveryCount() > 100).collect(
                Collectors.toList());
        if (!CollectionUtils.isEmpty(deadMessages)) {
          String[] deadMessagesIds = deadMessages.stream().map(PendingMessage::getId)
              .toArray(String[]::new);
          // 删除死信消息，防止重复被消费
          syncComm.xdel(redisStreamKey, deadMessagesIds);
          // 删除死信消息
          allPendingMessages.removeAll(deadMessages);
        }
      }

      if (!CollectionUtils.isEmpty(allPendingMessages)) {
        String[] pendingIds = allPendingMessages.stream().map(PendingMessage::getId)
            .toArray(String[]::new);
        // 认领超过30s未被处理的消息
        List<StreamMessage<String, String>> xclaimMessages = syncComm.xclaim(
            redisStreamKey, Consumer.from(consumerGroupName, consumerName), 30000, pendingIds);
        if (!CollectionUtils.isEmpty(xclaimMessages)) {
          return xclaimMessages;
        }
      }

      // 最多阻塞一秒等待最新消息
      return syncComm.xreadgroup(Consumer.from(consumerGroupName, consumerName),
          XReadArgs.Builder.block(1000), StreamOffset.lastConsumed(redisStreamKey));
    } catch (Exception e) {
      log.error("消息消费失败，RedisStreamKey：{} 消费者组：{} 消费者：{}， 错误信息:{}",
          redisStreamKey, consumerGroupName, consumerName, e.getMessage());
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  public void shutdown() {
    log.info("Shutdown reids stream consumer group.{}", this::toString);
    syncComm.xgroupDelconsumer(redisStreamKey, Consumer.from(consumerGroupName, consumerName));
    super.interrupt();
  }

  @Override
  public String toString() {
    return "RedisStreamKey：" + redisStreamKey +
        "ConsumerGroupName：" + consumerGroupName +
        "ConsumerName：" + consumerName;
  }
}
