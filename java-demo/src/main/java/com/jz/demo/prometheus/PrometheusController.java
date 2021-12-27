package com.jz.demo.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/prometheus/")
@RequiredArgsConstructor
public class PrometheusController {

    private final CollectorRegistry collectorRegistry;

    private Counter counter;

    @PostConstruct
    private void init() {
        counter = Counter.build().name("metrics_requests")
                .labelNames("path", "methods")
                .help("请求总数")
                .register(collectorRegistry);
    }

    @GetMapping(value = "test")
    public void test() {
        counter.labels("test", "get").inc();
    }

}
