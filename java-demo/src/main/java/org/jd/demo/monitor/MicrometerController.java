package org.jd.demo.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.CollectorRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/api/micrometer")
@RequiredArgsConstructor
public class MicrometerController {

    private final MeterRegistry meterRegistry;


    @GetMapping("/test")
    public Boolean init() {
        Counter counter = Counter.builder("micrometer_server_request_count")
                .tags("path", "/test")
                .tags("methods", "get")
                .description("请求总数")
                .register(meterRegistry);
        counter.increment();

//        Counter counter1 = Counter.builder("micrometer_server_request_count")
//                .tags("methods", "get")
//                .description("请求总数")
//                .register(meterRegistry);
//        counter1.increment();

        return true;
    }

}
