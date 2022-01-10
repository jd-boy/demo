package com.jz.demo.vertx.config;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.micrometer.MetricsDomain;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;
import io.vertx.micrometer.backends.BackendRegistries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class VertxConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    public static final Vertx vertx = Vertx.vertx(new VertxOptions()
            .setMetricsOptions(micrometerMetricsOptions())
            .setInternalBlockingPoolSize(11)
            .setMaxEventLoopExecuteTime(TimeUnit.SECONDS.toNanos(10))
            .setEventLoopPoolSize(10));

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        updateVertxMetrics();
        DeploymentOptions options = new DeploymentOptions()
                .setInstances(4)
                .setConfig(new JsonObject().put("http.path", "/test")
                        .put("http.port", 8088));
        vertx.deployVerticle("com.jz.demo.vertx.TestVerticle", options, System.out::println);
    }

    private static MetricsOptions micrometerMetricsOptions() {

        VertxPrometheusOptions prometheusOptions = new VertxPrometheusOptions()
                .setEnabled(true)
                .setStartEmbeddedServer(false);

        return new MicrometerMetricsOptions()
                .setEnabled(true)
                .setJvmMetricsEnabled(true)
                .setPrometheusOptions(prometheusOptions)
                .setLabelMatches(Collections.emptyList());
    }

    /**
     * @see MonitorConfiguration#prometheusMeterRegistry() 让SpringBoot使用Vertx创建的registry
     */
    private void updateVertxMetrics() {
        MeterRegistry meterRegistry = BackendRegistries.getDefaultNow();
        if (meterRegistry == null) {
            return;
        }
        meterRegistry.config()
                .meterFilter(meterFilter(MetricsDomain.HTTP_SERVER));
    }

    private MeterFilter meterFilter(MetricsDomain domain) {
        return new MeterFilter() {
            @Override
            public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
                if (id.getName().startsWith(domain.getPrefix())
                        && Meter.Type.TIMER == id.getType()) {
                    return DistributionStatisticConfig.builder()
                            .percentiles(0.95, 0.99, 0.995)
                            .build()
                            .merge(config);
                } else {
                    return config;
                }
            }
        };
    }

}
