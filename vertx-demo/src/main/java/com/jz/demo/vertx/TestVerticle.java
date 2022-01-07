package com.jz.demo.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class TestVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        HttpServer httpServer = vertx.createHttpServer();

        WorkerExecutor workerExecutor = vertx.createSharedWorkerExecutor("worker-pool", 10);

        String path = config().getString("http.path");
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route(HttpMethod.GET, path).handler(new TestHandler(workerExecutor));
        httpServer.requestHandler(router).listen(config().getInteger("http.port"));
    }

}
