package org.jd.demo.jpa.converter;

import org.jd.demo.jpa.domain.user.User;
import org.jd.demo.jpa.pojo.response.UserResponse;
import org.mapstruct.Mapper;

/**
 * @Auther jd
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

  UserResponse toUserResponse(User user);

}
