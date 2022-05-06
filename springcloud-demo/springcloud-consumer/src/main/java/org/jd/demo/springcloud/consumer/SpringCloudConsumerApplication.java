package org.jd.demo.springcloud.consumer;

import org.jd.demo.springcloud.api.service.UserService;
import org.jd.demo.springcloud.api.service.fallback.UserServiceFallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther jd
 */
@EnableFeignClients(basePackageClasses = UserService.class)
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {SpringCloudConsumerApplication.class,
    UserServiceFallback.class})
public class SpringCloudConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudConsumerApplication.class, args);
  }

}
