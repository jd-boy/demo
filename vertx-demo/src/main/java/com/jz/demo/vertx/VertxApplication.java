package com.jz.demo.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.micrometer.Label;
import io.vertx.micrometer.MetricsDomain;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class VertxApplication {

    private static final Vertx vertx = Vertx.vertx(new VertxOptions()
            .setMetricsOptions(micrometerMetricsOptions())
            .setInternalBlockingPoolSize(11)
            .setMaxEventLoopExecuteTime(TimeUnit.SECONDS.toNanos(10))
            .setEventLoopPoolSize(10));

    static {
        DeploymentOptions options = new DeploymentOptions()
                .setInstances(4)
                .setConfig(new JsonObject().put("http.path", "/test")
                        .put("http.port", 8088));
        vertx.deployVerticle("com.jz.demo.vertx.TestVerticle", options, System.out::println);
    }

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class, args);
    }

    private static MetricsOptions micrometerMetricsOptions() {

        VertxPrometheusOptions prometheusOptions = new VertxPrometheusOptions()
                .setStartEmbeddedServer(false) // use Spring Boot 2.x actuator endpoint
                .setEnabled(true);

        return new MicrometerMetricsOptions()
                .setEnabled(true)
                .setJvmMetricsEnabled(true)
                .setPrometheusOptions(prometheusOptions)
                .setLabelMatches(Collections.emptyList()) // remove default to improve performance
                .addDisabledMetricsCategory(MetricsDomain.NET_CLIENT)
                .addDisabledMetricsCategory(MetricsDomain.DATAGRAM_SOCKET)
                .addDisabledMetricsCategory(MetricsDomain.VERTICLES)
                .addDisabledMetricsCategory(MetricsDomain.EVENT_BUS)
                .setLabels(EnumSet.of(Label.REMOTE, Label.HTTP_PATH));
        // TODO: 把vertx从静态字段中移出来，转由Spring管理，从而能够使用配置文件进行自动配置
    }

}
