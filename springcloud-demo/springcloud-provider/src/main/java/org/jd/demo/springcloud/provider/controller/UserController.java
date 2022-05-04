package org.jd.demo.springcloud.provider.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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
  public UserDto findById(Integer userId) {
    return dataMap.get(userId);
  }

  @PostConstruct
  private void init() {
    dataMap = new HashMap<>();
    dataMap.put(1, new UserDto(1, "张三"));
    dataMap.put(2, new UserDto(2, "李四"));
  }

}