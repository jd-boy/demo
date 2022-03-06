package com.jz.demo.vertx.core;

import com.google.common.util.concurrent.RateLimiter;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
@Scope("prototype")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface VertxController {

  @AliasFor(annotation = Component.class)
  String value() default "";

  String path() default "";

  String description() default "";

  VertxHttpMethod[] method() default {};

  String port() default "8081";

  String instanceNum() default "1";

  /**
   * 接口限制的 QPS，默认不进行限制。
   * 限流使用 guava 的{@link RateLimiter} 实现，详细过程可查看{@link RateLimitHandler}
   * 支持从配置文件获取。
   * @return  最高QPS，该值需要大于0，类型为 Double
   */
  String permitsPerSecond() default "";

  /**
   * 限制QPS时，获取令牌的超时时间，单位：纳秒
   * 支持从配置文件获取。
   * @return 类型为 Long
   */
  String acquirePermitsTimeoutNanos() default "";

}
