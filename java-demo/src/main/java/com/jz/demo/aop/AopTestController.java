package com.jz.demo.aop;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@RestController
@RequiredArgsConstructor
public class AopTestController {

  @GetMapping(value = "/demo/aop/test")
  public Object get() {
    Map<String, String> map = new HashMap<>();
    map.put("12", "iji");
    System.out.println("method 执行");
    return map.get("12");
  }

}
