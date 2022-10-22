package org.jd.demo.jpa.converter;

import org.jd.demo.jpa.domain.usergroup.UserGroup;
import org.jd.demo.jpa.pojo.response.UserGroupResponse;
import org.mapstruct.Mapper;

/**
 * @Auther jd
 */
@Mapper(componentModel = "spring")
public interface UserGroupConverter {

  UserGroupResponse toUserGroupResponse(UserGroup userGroup);

}
