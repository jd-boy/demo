package org.jd.demo.springcloud.consumer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.RequiredArgsConstructor;
import org.jd.demo.springcloud.api.dto.Result;
import org.jd.demo.springcloud.api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@RestController
@RequiredArgsConstructor
public class FeignTestController {

  private final UserService userService;

  @SentinelResource(value = "Consumer-findById", fallback = "fallback")
  @GetMapping(value = "/api/springcloud/feign/user/{id}")
  public Result findById(@PathVariable("id") Integer id) {
    return userService.findById(id);
  }

  public Result fallback(Integer userId, Throwable e) {
    e.printStackTrace();
    return Result.fail("Consumer Sentinel 熔断");
  }

}
