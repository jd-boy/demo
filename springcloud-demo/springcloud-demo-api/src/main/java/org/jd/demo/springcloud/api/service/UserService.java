package org.jd.demo.springcloud.api.service;

import org.jd.demo.springcloud.api.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther jd
 */
// value需要与生产者名称一致
@FeignClient(value = "spring-cloud-demo-provider")
public interface UserService {

  @GetMapping(value = "/api/springcloud/users/{id}")
  Result findById(@PathVariable("id") Integer userId);

}
