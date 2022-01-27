package com.jz.demo.vertx.controller;

import com.jz.demo.vertx.core.AbstractVertxController;
import com.jz.demo.vertx.core.VertxController;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;

/**
 * @Auther jd
 */
@VertxController(
    path = "/test/redis"
)
public class RedisTestVertxController extends AbstractVertxController {

  private final Redis redis;

  public RedisTestVertxController(Redis redis, Router router) {
    super(router);
    this.redis = redis;
  }

  @Override
  public void handle(RoutingContext context) {
    redis.send(Request.cmd(Command.LPUSH).arg("messageQueue").arg("消息"))
        .onComplete(handler -> {
          if (handler.succeeded()) {
            context.response().end("成功");
          } else {
            context.response().setStatusCode(500).end("服务异常");
          }
        });
  }

}