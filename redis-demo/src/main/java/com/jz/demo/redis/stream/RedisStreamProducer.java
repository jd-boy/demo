package com.jz.demo.redis.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.XAddArgs;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * @Auther jd
 */
@Slf4j
public class RedisStreamProducer<M> {

  private final RedisCommands<String, String> syncComm;

  private final Queue<M> cacheQueue;

  private final String redisStreamKeyPrefix;

  private final int redisStreamNum;

  private final long redisStreamMaxLen;

  private final String redisStreamFieldKey;

  private final int batchSize;

  private final ObjectMapper objectMapper;

  private ScheduledExecutorService executor;

  public RedisStreamProducer(String redisStreamKeyPrefix, String redisStreamFieldKey, int redisStreamNum,
      int redisStreamMaxLen, int batchSize, Queue<M> cacheQueue, RedisCommands<String, String> syncComm) {
    Assert.hasText(redisStreamKeyPrefix, "RedisStreamKeyPrefix cannot be empty");
    Assert.isTrue(redisStreamNum >= 0, "RedisStreamNum must be greater than or equal to 0");
    Assert.isTrue(redisStreamMaxLen >= 0, "RedisStreamMaxLen must be greater than or equal to 0");
    Assert.hasText(redisStreamFieldKey, "RedisStreamFieldKey cannot be empty");
    Assert.isTrue(batchSize >= 0, "BatchSize must be greater than or equal to 0");
    Assert.notNull(syncComm, "SyncComm cannot be empty");
    this.redisStreamKeyPrefix = redisStreamKeyPrefix;
    this.redisStreamNum = redisStreamNum;
    this.redisStreamMaxLen = redisStreamMaxLen;
    this.redisStreamFieldKey = redisStreamFieldKey;
    this.batchSize = batchSize;
    this.cacheQueue = cacheQueue;
    this.syncComm = syncComm;
    this.objectMapper = new ObjectMapper();
  }

  public void strat() {
    executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(new WorkTask(), 1, 1, TimeUnit.SECONDS);
    log.info("Strat redis stream producer");
  }

  public void shutdown() {
    if (Objects.nonNull(executor)) {
      executor.shutdown();
    }
    log.info("Shutdown redis stream producer");
  }

  public void send(M message) {
    cacheQueue.add(message);
  }

  private void sendMsg(List<M> list) {
    sendMsg(getRedisStreamKey(), redisStreamFieldKey, list);
  }

  @SneakyThrows
  private void sendMsg(String redisStreamKey, String fieldKey, List<M> list) {
    if (CollectionUtils.isEmpty(list)) {
      return;
    }
    if (redisStreamMaxLen == 0) {
      syncComm.xadd(redisStreamKey, fieldKey, list);
      return;
    }
    syncComm.xadd(redisStreamKey, XAddArgs.Builder.maxlen(redisStreamMaxLen),
        fieldKey, objectMapper.writeValueAsString(list));
    log.debug("RedisStreamKey：{} FieldKey: {} 发送 {} 条消息",
        redisStreamKey, fieldKey, list.size());
  }

  private String getRedisStreamKey() {
    if (redisStreamNum == 0) {
      return redisStreamKeyPrefix;
    }
    return redisStreamKeyPrefix + (System.currentTimeMillis() % redisStreamNum);
  }

  private class WorkTask implements Runnable {

    @Override
    public void run() {
      int count = 0;
      M msg = null;
      List<M> list = new ArrayList<>(batchSize);
      while ((msg = cacheQueue.poll()) != null) {
        list.add(msg);
        count++;
        if (count == batchSize) {
          sendMsg(list);
          list = new ArrayList<>(batchSize);
          count = 0;
        }
      }
      if (!list.isEmpty()) {
        sendMsg(list);
      }
    }

  }

}