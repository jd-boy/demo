package com.jz.demo.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * @Auther jd
 */
public abstract class AbstractVertxController extends AbstractVerticle implements Handler<RoutingContext> {

  private final Router router;

  public AbstractVertxController(Router router) {
    this.router = router;
  }

  @Override
  public void start() throws Exception {
    VertxController vertxController = AnnotatedElementUtils
        .getMergedAnnotation(this.getClass(), VertxController.class);
    HttpServer httpServer = vertx.createHttpServer();
    Route route = router.route(vertxController.path());
    for (VertxHttpMethod httpMethod : vertxController.method()) {
      route.method(httpMethod.getMethod());
    }
    route.handler(BodyHandler.create())
        .handler(this);
    httpServer.requestHandler(router)
        .listen(Integer.valueOf(SpringUtil.resolvePlaceholders(vertxController.port())));
  }

}
