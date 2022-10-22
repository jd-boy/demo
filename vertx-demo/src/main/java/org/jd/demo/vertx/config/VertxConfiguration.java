package org.jd.demo.vertx.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.ext.web.Router;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class VertxConfiguration {

    @Bean
    Router router(Vertx vertx) {
        return Router.router(vertx);
    }

    @Bean
    Vertx vertx(PrometheusMeterRegistry prometheusMeterRegistry) {
        return Vertx.vertx(new VertxOptions()
                .setMetricsOptions(micrometerMetricsOptions(prometheusMeterRegistry))
                .setInternalBlockingPoolSize(11)
                .setMaxEventLoopExecuteTime(TimeUnit.SECONDS.toNanos(10))
                .setEventLoopPoolSize(10));
    }

    private static MetricsOptions micrometerMetricsOptions(PrometheusMeterRegistry prometheusMeterRegistry) {
        VertxPrometheusOptions prometheusOptions = new VertxPrometheusOptions()
                .setEnabled(true)
                .setStartEmbeddedServer(false);

        return new MicrometerMetricsOptions()
                .setMicrometerRegistry(prometheusMeterRegistry)
                .setEnabled(true)
                .setJvmMetricsEnabled(true)
                .setPrometheusOptions(prometheusOptions)
                .setLabelMatches(Collections.emptyList());
    }

}
