package com.jz.demo.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class VertxConfigration {

    private static final Vertx vertx = Vertx.vertx(new VertxOptions()
            .setInternalBlockingPoolSize(11)
//            .setMaxEventLoopExecuteTime(TimeUnit.SECONDS.toNanos(10))
            .setEventLoopPoolSize(10));

    static {
        DeploymentOptions options = new DeploymentOptions()
                .setInstances(2)
                .setConfig(new JsonObject().put("http.path", "/test")
                        .put("http.port", 8088));
        vertx.deployVerticle("com.jz.demo.vertx.TestVerticle", options, System.out::println);
    }

}
