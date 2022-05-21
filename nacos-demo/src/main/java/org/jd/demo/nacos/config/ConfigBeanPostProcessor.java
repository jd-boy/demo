package org.jd.demo.nacos.config;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
@RequiredArgsConstructor
public class ConfigBeanPostProcessor implements BeanPostProcessor, EnvironmentAware {

  private final static Executor EXECUTOR = Executors
      .newSingleThreadExecutor(r -> new Thread(r, "nacos-config-refresh"));

  private final ConfigService configService;

  private final ObjectMapper objectMapper;

  private Environment environment;

  @SneakyThrows
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (!(bean instanceof ConfigListener)) {
      return bean;
    }
    Class<?> clazz = bean.getClass();
    NacosConfigListener nacosConfigListener = clazz.getAnnotation(NacosConfigListener.class);
    if (nacosConfigListener == null) {
      return bean;
    }
    Type type = null;
    for (Type genericInterface : clazz.getGenericInterfaces()) {
      String typeName = genericInterface.getTypeName();
      typeName = typeName.substring(0, genericInterface.getTypeName().indexOf('<'));
      if (ConfigListener.class.getTypeName().equals(typeName)) {
        type = genericInterface;
        break;
      }
    }

    ConfigInfo configInfo = buildConfigInfo(nacosConfigListener);

    Type targetType = ((ParameterizedType) type).getActualTypeArguments()[0];
    configInfo.setConverter(str -> {
      try {
        return objectMapper.readValue(str,
            objectMapper.getTypeFactory().constructType(targetType));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return null;
    });
    Listener listener = new NacosConfigRefreshListener(configInfo, (ConfigListener<?>) bean, EXECUTOR);

    listener.receiveConfigInfo(
        configService.getConfigAndSignListener(configInfo.getDataId(), configInfo.getGroup(),
            1000, listener));

    return bean;
  }

  private ConfigInfo buildConfigInfo(NacosConfigListener nacosConfigListener) {
    return ConfigInfo.builder()
        .name(nacosConfigListener.name())
        .group(environment.resolvePlaceholders(nacosConfigListener.group()))
        .dataId(environment.resolvePlaceholders(nacosConfigListener.dataId()))
        .build();
  }

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }




  @Slf4j
  static class NacosConfigRefreshListener implements Listener {

    private final Executor executor;

    private final ConfigListener configListener;

    private final ConfigInfo configInfo;

    public NacosConfigRefreshListener(ConfigInfo configInfo, ConfigListener<?> configListener, Executor executor) {
      this.executor = executor;
      this.configInfo = configInfo;
      this.configListener = configListener;
    }

    @Override
    public Executor getExecutor() {
      return executor;
    }

    @Override
    @SneakyThrows
    public void receiveConfigInfo(String configInfoStr) {
      log.info("group：{} dataId：{} update {} 配置：{}", configInfo.getGroup(), configInfo.getDataId(),
          configInfo.getName(), configInfoStr);
      configListener.loadConfig(configInfo.getConverter().apply(configInfoStr));
    }

  }

}