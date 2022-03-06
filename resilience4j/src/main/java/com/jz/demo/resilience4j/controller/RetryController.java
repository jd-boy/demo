package com.jz.demo.resilience4j.controller;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
public class RetryController {

  @Retry(name = "backendA", fallbackMethod = "retryFallback")
  @GetMapping(value = "/api/resilience4j/retry")
  public Object testRetry(boolean isRetry) {
    if (isRetry) {
      throw new RuntimeException("重试异常");
    }
    return "success";
  }

  private Object retryFallback(Throwable throwable) {
    return "重试 fallback";
  }

}
