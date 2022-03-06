package com.jz.demo.resilience4j.controller;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流测试controller
 * @Auther jd
 */
@Slf4j
@RestController
public class RateLimiterController {

  @RateLimiter(name = "backendA", fallbackMethod = "rateLimiterFallback")
  @GetMapping(value = "/api/resilience4j/rate-limiter")
  public Object testRateLimiter() {
    return "rate-limiter";
  }

  private Object rateLimiterFallback(Throwable e) {
    if (e instanceof RequestNotPermitted) {
      log.warn("超过设定qps Fallback");
      return "超过设定qps";
    }
    log.warn("测试限流接口异常");
    return "测试限流接口异常";
  }

}
