package com.jz.demo.vertx.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
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

  String instanceNum() default "";

}
