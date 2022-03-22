package com.jz.demo.jpa.converter;

import com.jz.demo.jpa.domain.user.User;
import com.jz.demo.jpa.pojo.response.UserResponse;
import org.mapstruct.Mapper;

/**
 * @Auther jd
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

  UserResponse toUserResponse(User user);

}
