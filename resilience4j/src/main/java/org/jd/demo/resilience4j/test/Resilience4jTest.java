package org.jd.demo.resilience4j.test;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction1;
import io.vavr.control.Try;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class Resilience4jTest {

    public static void main(String[] args) throws Throwable {
        var registry = CircuitBreakerRegistry.ofDefaults();
        var config = getConfig();
        var brek1 = registry.circuitBreaker("redis", config);
        var brek2 = registry.circuitBreaker("redis");
        System.out.println(brek1 == brek2);
        for (int i = 0; i < 60; i++) {
            final int ii = i + 1;
            if (ii == 20) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            var aa = CircuitBreaker.decorateSupplier(brek1, () -> {
//                if (ii >= 10) {
//                    return new Object();
//                } else {
//                    throw new RuntimeException("ijidf");
//                }
//            });
//            Try.ofSupplier(aa)
//                  .onFailure(throwable -> System.out.printf("第 %d 次失败，断路器状态：%s，异常:%s\n", ii, brek1.getState(), throwable.getMessage()))
//                  .onSuccess(o -> System.out.printf("第 %d 次成功，断路器状态：%s\n", ii, brek1.getState()));

            if (ii >= 10) {
                brek1.onSuccess(1, brek1.getTimestampUnit());
                System.out.printf("第 %d 次成功，断路器状态：%s\n", ii, brek1.getState());
            } else {
                brek1.onError(1, brek1.getTimestampUnit(), new RuntimeException("ijidf"));
                System.out.printf("第 %d 次失败，断路器状态：%s\n", ii, brek1.getState());
            }
        }
    }

    private static CircuitBreakerConfig getConfig() {
        return CircuitBreakerConfig.custom()
              // 失败率
              .failureRateThreshold(10)
              // 慢调用失败率
              .slowCallRateThreshold(50)
              // 从 open 状态 转为 half open 状态所需时间
              .waitDurationInOpenState(Duration.ofMillis(100))
              // 多长时间被视为慢调用
              .slowCallDurationThreshold(Duration.ofSeconds(2))
              // half open 状态允许尝试的次数
              .permittedNumberOfCallsInHalfOpenState(3)
              // 自动从 open 状态 转变为 half open 状态
              .automaticTransitionFromOpenToHalfOpenEnabled(true)
              // 最小请求数量，只有在滑动窗口内,请求个数达到这个个数,才会触发CircuitBreaker对于是否打开断路器的判断，该值超过滑动窗口数量时，
              // 当失败数达到滑动窗口大小依然会 open
              .minimumNumberOfCalls(10)
              // 滑动窗口类型
              .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
              // 滑动窗口大小
              .slidingWindowSize(50)
//              .recordExceptions(IOException.class, TimeoutException.class)
//              .ignoreExceptions(BusinessException.class, OtherBusinessException.class)
              .build();
    }

}
