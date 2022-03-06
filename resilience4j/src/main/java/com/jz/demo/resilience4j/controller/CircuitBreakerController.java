package com.jz.demo.resilience4j.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
public class CircuitBreakerController {

  @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
  @GetMapping(value = "/api/resilience4j/circuit-breaker")
  public Object testCircuitBreaker() {
    if (true) {
      throw new RuntimeException("测试熔断异常");
    }
    return "circuit-breake";
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
