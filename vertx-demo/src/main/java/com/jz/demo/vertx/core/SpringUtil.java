package com.jz.demo.vertx.core;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Auther jd
 */
@Component
public class SpringUtil implements EnvironmentAware {

  private static Environment environment;

  public static Double resolvePlaceholdersToDouble(String text) {
    String value = resolvePlaceholders(text);
    return StringUtils.hasText(value) ? Double.valueOf(value) : null;
  }

  public static Long resolvePlaceholdersToLong(String text) {
    String value = resolvePlaceholders(text);
    return StringUtils.hasText(value) ? Long.valueOf(value) : null;
  }

  public static Integer resolvePlaceholdersToInteger(String text) {
    String value = resolvePlaceholders(text);
    return StringUtils.hasText(value) ? Integer.valueOf(value) : null;
  }

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
