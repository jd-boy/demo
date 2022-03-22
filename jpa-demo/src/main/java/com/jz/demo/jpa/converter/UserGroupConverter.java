package com.jz.demo.jpa.converter;

import com.jz.demo.jpa.domain.usergroup.UserGroup;
import com.jz.demo.jpa.pojo.response.UserGroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther jd
 */
@Mapper(componentModel = "spring")
public interface UserGroupConverter {

  UserGroupResponse toUserGroupResponse(UserGroup userGroup);

}
