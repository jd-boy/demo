package com.jz.demo.vertx.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.jd.zeus.vertx.springboot.core.AbstractHttpVertxVerticle;
import org.jd.zeus.vertx.springboot.core.VertxController;

/**
 * @Auther jd
 */
@VertxController(path = "/api/vertx/test",
    port = "8081",
    instanceNum = "4",
    permitsPerSecond = "1",
    acquirePermitsTimeoutNanos = "10000"
)
public class TestVertxController extends AbstractHttpVertxVerticle {

  public TestVertxController(Router router) {
    super(router);
  }

  @Override
  public void handle(RoutingContext context) {
    JsonObject json = context.getBodyAsJson();

    if (!context.response().ended()) {
      context.response()
          .putHeader("Content-Type", "application/json;charset=utf8")
          .end("结束", "utf-8");
    }
  }

}
