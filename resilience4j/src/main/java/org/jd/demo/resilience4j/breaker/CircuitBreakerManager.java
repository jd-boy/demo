package org.jd.demo.resilience4j.breaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class CircuitBreakerManager {

    private static CircuitBreakerRegistry circuitBreakerRegistry;

    public CircuitBreakerManager(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreakerManager.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    public static CircuitBreaker get(String name) {
        return circuitBreakerRegistry.circuitBreaker(name);
    }

}
