package org.jd.demo.springcloud.api.service.fallback;

import org.jd.demo.springcloud.api.dto.Result;
import org.jd.demo.springcloud.api.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
public class UserServiceFallback implements UserService {

  @Override
  public Result findById(Integer userId) {
    return Result.fail("Feign 熔断");
  }
}
