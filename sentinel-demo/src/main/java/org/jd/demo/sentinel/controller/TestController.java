package org.jd.demo.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
public class TestController {

  @SentinelResource(value = "test")
  @GetMapping(value = "/api/sentinel/test")
  public Object get() {
    return "test";
  }

}
