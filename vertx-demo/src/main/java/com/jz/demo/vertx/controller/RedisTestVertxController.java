package com.jz.demo.vertx.controller;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;
import org.jd.zeus.vertx.springboot.core.AbstractHttpVertxVerticle;
import org.jd.zeus.vertx.springboot.core.VertxController;

/**
 * @Auther jd
 */
@VertxController(
    path = "/test/redis"
)
public class RedisTestVertxController extends AbstractHttpVertxVerticle {

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