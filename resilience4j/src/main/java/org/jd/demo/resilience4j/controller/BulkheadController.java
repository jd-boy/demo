package org.jd.demo.resilience4j.controller;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
public class BulkheadController {

  @Bulkhead(name = "backendA", fallbackMethod = "semaphoreBulkheadFallbackMethod")
  @GetMapping(value = "/api/resilience4j/semaphore-bulkhead")
  public Object testSemaphoreBulkhead() throws InterruptedException {
    log.info("进入信号量隔离方法");
    Thread.sleep(1000);
    return "success";
  }

  /**
   * @return 返回值类型必须是（或继承自） CompletableFuture
   * @throws InterruptedException
   */
  @Bulkhead(name = "backendC", type = Type.THREADPOOL, fallbackMethod = "threadpoolBulkheadFallbackMethod")
  @GetMapping(value = "/api/resilience4j/threadpool-bulkhead")
  public CompletableFuture<String> testThreadPoolBulkhead() throws InterruptedException {
    log.info("进入线程池隔离方法");
    Thread.sleep(1000);
    return CompletableFuture.completedFuture("success");
  }

  /**
   * 信号量隔离 fallback 方法
   * @param throwable
   * @return
   */
  private Object semaphoreBulkheadFallbackMethod(Throwable throwable) {
    if (throwable instanceof BulkheadFullException) {
      log.warn("信号量隔离-超过最大并发量");
    } else {
      throwable.printStackTrace();
    }
    return "信号量隔离 fallback";
  }

  /**
   * 线程池隔离 fallback 方法
   * @param throwable
   * @return 返回值类型必须是（或继承自） CompletableFuture
   */
  private CompletableFuture<String> threadpoolBulkheadFallbackMethod(Throwable throwable) {
    if (throwable instanceof BulkheadFullException) {
      log.warn("线程池隔离-超过最大并发量");
    } else {
      throwable.printStackTrace();
    }
    return CompletableFuture.completedFuture("线程池隔离 fallback");
  }

}
