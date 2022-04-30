package org.jd.demo.dubbo.provider.service;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.dubbo.config.annotation.DubboService;
import org.jd.demo.dubbo.api.dto.UserDto;
import org.jd.demo.dubbo.api.service.UserService;

/**
 * @Auther jd
 */
@DubboService
public class UserServiceImpl implements UserService {

  private Map<Integer, UserDto> map = new HashMap<>();

  @PostConstruct
  private void init() {
    map.put(1, UserDto.builder().id(1).name("张三").build());
  }

  @Override
  public UserDto findById(Integer id) {
    return map.get(id);
  }

}
