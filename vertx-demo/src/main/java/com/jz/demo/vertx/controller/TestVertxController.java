package com.jz.demo.vertx.controller;

import com.jz.demo.vertx.core.AbstractVertxController;
import com.jz.demo.vertx.core.VertxController;
import com.jz.demo.vertx.core.VertxHttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @Auther jd
 */
@VertxController(path = "/api/vertx/test",
    port = "8081",
    instanceNum = "4",
    permitsPerSecond = "1",
    acquirePermitsTimeoutNanos = "10000"
)
public class TestVertxController extends AbstractVertxController {

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
