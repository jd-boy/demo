package com.jz.demo.vertx.controller;

import com.jz.demo.vertx.autoconfig.AbstractHttpVertxVerticle;
import com.jz.demo.vertx.autoconfig.VertxController;
import com.jz.demo.vertx.autoconfig.VertxHttpMethod;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

/**
 * @Auther jd
 */
@Slf4j
@VertxController(path = "/api/vertx/test",
    method = VertxHttpMethod.GET,
    instanceNum = "4",
    permitsPerSecond = "1",
    acquirePermitsTimeoutNanos = "10000"
)
public class TestVertxController extends AbstractHttpVertxVerticle {

  @Autowired
  private Vertx vertx;

  private WebClient webClient;

  @PostConstruct
  public void init() {
    webClient = WebClient.create(vertx,
        new WebClientOptions()
            .setMaxPoolSize(1)
            .setKeepAliveTimeout(3600)
            .setIdleTimeout(3600)
            .setKeepAlive(true));
  }

  public TestVertxController(Router router) {
    super(router);
  }

  @Override
  public void handle(RoutingContext context) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    long timeout = Long.parseLong(context.queryParam("timeout").get(0));
    
    webClient.get(8001, "127.0.0.1", "/api/vertx/client/test1?timeout=" + timeout)
        .timeout(5000)
        .send()
        .onSuccess(response -> {
          log.info("请求：{}", response.body().toString());
          stopWatch.stop();
          response(context, "成功" + stopWatch.getLastTaskTimeMillis());
        })
        .onFailure(err -> {
          log.info("请求失败：{}", err.toString());
          stopWatch.stop();
          response(context, "失败" + stopWatch.getLastTaskTimeMillis());
        });

  }

  private void response(RoutingContext context, String msg) {
    if (!context.response().ended()) {
      context.response()
          .putHeader("Content-Type", "application/json;charset=utf8")
          .end(msg, "utf-8");
    }
  }

}
