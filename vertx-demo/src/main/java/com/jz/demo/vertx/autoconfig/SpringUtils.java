package com.jz.demo.vertx.autoconfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Auther jd
 */
@Component
public class SpringUtils implements EnvironmentAware, BeanFactoryAware {

  private static Environment environment;

  private static BeanFactory beanFactory;

  public static <T> T getBean(Class<T> requiredType) {
    return beanFactory.getBean(requiredType);
  }

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

  /**
   * 解析 SPEL 表达式
   * @param text SPEL 表达式
   * @return  解析的结果
   */
  public static String resolvePlaceholders(String text) {
    return environment.resolvePlaceholders(text);
  }

  @Override
  public void setEnvironment(Environment environment) {
    if (SpringUtils.environment == null) {
      SpringUtils.environment = environment;
    }
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    if (SpringUtils.beanFactory == null) {
      SpringUtils.beanFactory = beanFactory;
    }
  }

}