package org.jd.demo.dubbo.api.service;

import org.jd.demo.dubbo.api.dto.UserDto;

/**
 * @Auther jd
 */
public interface UserService {

  UserDto findById(Integer id);

}
