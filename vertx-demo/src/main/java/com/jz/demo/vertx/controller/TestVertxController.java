package com.jz.demo.vertx.controller;

import com.jz.demo.vertx.core.AbstractVertxController;
import com.jz.demo.vertx.core.VertxController;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @Auther jd
 */
@VertxController(path = "/test",
    port = "8088",
    instanceNum = "4"
)
public class TestVertxController extends AbstractVertxController {

  public TestVertxController(Router router) {
    super(router);
  }

  @Override
  public void handle(RoutingContext context) {
    JsonObject json = context.getBodyAsJson();

    if (!context.response().ended()) {
      context.response().end("结束", "utf-8");
    }
  }

}
