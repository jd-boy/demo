package org.jd.demo.springcloud.provider.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jd.demo.springcloud.api.dto.Result;
import org.jd.demo.springcloud.api.dto.UserDto;
import org.jd.demo.springcloud.api.service.UserService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
public class UserController implements UserService {

  private Map<Integer, UserDto> dataMap;

  @Override
  @SentinelResource(value = "Provider-findById", fallback = "fallback")
  public Result findById(Integer userId) {
//    if (userId == -1) {
//      throw new RuntimeException("测试熔断");
//    }
    UserDto result = dataMap.get(userId);
    if (result == null) {
      log.info("未找到用户");
      throw new RuntimeException("未找到用户");
    }
    return Result.success(result);
  }

  public Result fallback(Integer userId, Throwable e) {
    return Result.fail("Provider Sentinel 熔断");
  }

  @PostConstruct
  private void init() {
    dataMap = new HashMap<>();
    dataMap.put(1, new UserDto(1, "张三"));
    dataMap.put(2, new UserDto(2, "李四"));
  }

}