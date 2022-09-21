package com.jz.demo.vertx.autoconfig;

import com.google.common.util.concurrent.RateLimiter;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.concurrent.TimeUnit;
import org.springframework.util.Assert;

/**
 * @Auther jz
 */
public class RateLimitHandler implements Handler<RoutingContext> {

  private final RateLimiter rateLimiter;

  private final long timeout;

  private final TimeUnit timeUnit;

  public RateLimitHandler(double permitsPerSecond, Long timeout, TimeUnit timeUnit) {
    Assert.isTrue(permitsPerSecond > 0, "PermitsPerSecond must be greater than 0");
    Assert.notNull(timeout, "Timeout cannot be null");
    Assert.isTrue(timeout > 0, "Timeout must be greater than 0");
    Assert.notNull(timeUnit, "TimeUnit cannot be null");
    this.rateLimiter = RateLimiter.create(permitsPerSecond);
    this.timeout = timeout;
    this.timeUnit = timeUnit;
  }

  @Override
  public void handle(RoutingContext context) {
    if (!rateLimiter.tryAcquire(timeout, timeUnit)) {
      context.response()
          .setStatusCode(500)
          .putHeader("Content-Type", "application/json;charset=utf-8")
          .end("请求频率过高");
    } else {
      context.next();
    }
  }

}