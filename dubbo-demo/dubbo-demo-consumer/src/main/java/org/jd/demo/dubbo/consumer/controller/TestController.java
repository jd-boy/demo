package org.jd.demo.dubbo.consumer.controller;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.jd.demo.dubbo.api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@RestController
@RequiredArgsConstructor
public class TestController {

  @DubboReference
  private UserService userService;

  @GetMapping(value = "/api/dubbo/test")
  public Object test(Integer id) {
    return userService.findById(id);
  }

}
