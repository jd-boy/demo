package com.jz.demo.resilience4j.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/resilience4j")
public class TestController {

  @RateLimiter(name = "test", fallbackMethod = "rateLimiterFallback")
  @GetMapping(value = "/rate-limiter")
  public Object testRateLimiter() {
    return "rate-limiter";
  }

  @CircuitBreaker(name = "test", fallbackMethod = "circuitBreakerFallback")
  @GetMapping(value = "/circuit-breaker")
  public Object testCircuitBreaker() {
    if (true) {
      throw new RuntimeException("测试熔断异常");
    }
    return "circuit-breake";
  }

  private Object rateLimiterFallback(Throwable e) {
    if (e instanceof RequestNotPermitted) {
      log.warn("超过设定qps Fallback");
      return "超过设定qps";
    }
    log.warn("测试限流接口异常");
    return "测试限流接口异常";
  }

  private Object circuitBreakerFallback(RuntimeException e) {
    if (e instanceof CallNotPermittedException) {
      log.warn("达到熔断Fallback");
      return "达到熔断Fallback";
    }
    log.warn("熔断测试接口异常");
    return "熔断测试接口异常";
  }

}
