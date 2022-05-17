package org.jd.demo.nacos.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther jd
 */
@Configuration
@RequiredArgsConstructor
public class AutoConfiguration {

  @Bean
  @SneakyThrows
  public ConfigService configService(NacosServiceProperty nacosServiceProperty) {
    Properties properties = new Properties();
    properties.put(PropertyKeyConst.SERVER_ADDR, nacosServiceProperty.getServerAddr());
    properties.put(PropertyKeyConst.USERNAME, nacosServiceProperty.getUsername());
    properties.put(PropertyKeyConst.PASSWORD, nacosServiceProperty.getPassword());
    properties.put(PropertyKeyConst.NAMESPACE, nacosServiceProperty.getNamespace());
    return NacosFactory.createConfigService(properties);
  }

}
