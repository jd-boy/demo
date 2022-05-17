package org.jd.demo.nacos.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NacosConfigLoader {

  /**
   * 配置名称
   * @return
   */
  String name();

  /**
   * Nacos group
   * @return
   */
  String group() default "";

  /**
   * Nacos dataId
   * @return
   */
  String dataId() default "";

}
