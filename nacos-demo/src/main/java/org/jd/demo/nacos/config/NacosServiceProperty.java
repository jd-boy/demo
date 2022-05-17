package org.jd.demo.nacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.cloud.nacos.config")
public class NacosServiceProperty {

  private String serverAddr;

  private String namespace = "";

  private String username;

  private String password;

}
