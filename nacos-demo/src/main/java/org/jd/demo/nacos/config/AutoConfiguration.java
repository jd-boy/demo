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
  public ConfigService configService(NacosServiceInfo nacosServiceInfo) {
    Properties properties = new Properties();
    properties.put(PropertyKeyConst.SERVER_ADDR, nacosServiceInfo.getServerAddr());
    properties.put(PropertyKeyConst.USERNAME, nacosServiceInfo.getUsername());
    properties.put(PropertyKeyConst.PASSWORD, nacosServiceInfo.getPassword());
    properties.put(PropertyKeyConst.NAMESPACE, nacosServiceInfo.getNamespace());
    return NacosFactory.createConfigService(properties);
  }

}
