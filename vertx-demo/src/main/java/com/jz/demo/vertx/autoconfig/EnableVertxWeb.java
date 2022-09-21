package com.jz.demo.vertx.autoconfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @Auther jd
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(VertxWebImportSelector.class)
public @interface EnableVertxWeb {

  String[] value() default {};

  String[] basePackages() default {};

  Class<?>[] basePackageClasses() default {};

}
