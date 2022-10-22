package org.jd.demo.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@RestController
@RequiredArgsConstructor
public class AopTestController {

  @SneakyThrows
  @GetMapping(value = "/demo/aop/test")
  public Object get() {
//    Thread.sleep(1000);
    Map<String, String> map = new HashMap<>();
    map.put("12", "iji");
    System.out.println("method 执行");
    return map.get("12");
  }

}
