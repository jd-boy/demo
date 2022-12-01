package org.jd.demo.resilience4j.breaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerAutoConfiguration {

    @Bean
    CircuitBreakerManager circuitBreakerManager(CircuitBreakerRegistry circuitBreakerRegistry) {
        return new CircuitBreakerManager(circuitBreakerRegistry);
    }


}
