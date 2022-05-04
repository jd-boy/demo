package org.jd.demo.springcloud.consumer.controller;

import lombok.RequiredArgsConstructor;
import org.jd.demo.springcloud.api.dto.UserDto;
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

  @GetMapping(value = "/api/springcloud/feign/user/{id}")
  public UserDto findById(@PathVariable("id") Integer id) {
    return userService.findById(id);
  }

}
