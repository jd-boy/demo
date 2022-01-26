package com.jz.demo.vertx.core;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
public class SpringUtil implements EnvironmentAware {

  private static Environment environment;

  public static String resolvePlaceholders(String text) {
    return environment.resolvePlaceholders(text);
  }

  @Override
  public void setEnvironment(Environment environment) {
    if (SpringUtil.environment == null) {
      SpringUtil.environment = environment;
    }
  }

}
