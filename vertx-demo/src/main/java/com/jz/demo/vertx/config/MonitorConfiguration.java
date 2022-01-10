package com.jz.demo.vertx.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.vertx.micrometer.backends.BackendRegistries;
import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MonitorConfiguration {

    /**
     * 由于Vertx在SpringBoot之前初始化，自动配置失效，使用vertx的MeterRegistry
     */
    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return (PrometheusMeterRegistry) BackendRegistries.getDefaultNow();
    }

    /**
     * 需要同时定义MeterRegistry和CollectorRegistry两个Bean才能替换掉SpringBoot自动配置.
     *
     *  @see PrometheusMetricsExportAutoConfiguration
     */
    @Bean
    public CollectorRegistry collectorRegistry(PrometheusMeterRegistry prometheusMeterRegistry) {
        return prometheusMeterRegistry.getPrometheusRegistry();
    }

}
