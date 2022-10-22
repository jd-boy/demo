package org.jd.demo.vertx.controller;

import org.jd.demo.vertx.autoconfig.AbstractHttpVertxVerticle;
import org.jd.demo.vertx.autoconfig.VertxController;
import org.jd.demo.vertx.autoconfig.VertxHttpMethod;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLPool;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther jd
 */
@VertxController(
    path = "/test/db",
    method = VertxHttpMethod.GET,
    instanceNum = "3"
)
public class DBTestVertxController extends AbstractHttpVertxVerticle {

  private final MySQLPool mysqlPool;

  public DBTestVertxController(MySQLPool mysqlPool, Router router) {
    super(router);
    this.mysqlPool = mysqlPool;
  }

  @Override
  public void handle(RoutingContext context) {
    Set<String> sqls = new HashSet<>();
    List<Object> result = new ArrayList<>();
    List<Future> tasks = sqls.stream()
        .map(sql -> mysqlPool.query(sql)
              .mapping(Function.identity())
              .execute()
              .onComplete(handler -> {
                if (handler.succeeded()) {
                  handler.result().forEach(result::add);
                }
              })
        ).collect(Collectors.toList());

    CompositeFuture.all(tasks)
        .onSuccess(handler -> {
          context.response().end(Json.encodeToBuffer(result));
        })
        .onFailure(handler -> {
          context.response().setStatusCode(500).end("服务异常");
        });
  }

}