package org.jd.demo.springcloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther jd
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudProviderApplication.class, args);
  }

}
