package com.jz.demo.jpa.controller;

import com.jz.demo.jpa.application.UserApplication;
import com.jz.demo.jpa.application.UserGroupApplication;
import com.jz.demo.jpa.converter.UserConverter;
import com.jz.demo.jpa.converter.UserGroupConverter;
import com.jz.demo.jpa.domain.permission.Permission;
import com.jz.demo.jpa.pojo.request.UserGroupRequest;
import com.jz.demo.jpa.pojo.request.UserRequest;
import com.jz.demo.jpa.pojo.response.UserGroupResponse;
import com.jz.demo.jpa.pojo.response.UserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

  private final UserConverter userConverter;

  private final UserGroupConverter userGroupConverter;

  private final UserApplication userApplication;

  private final UserGroupApplication userGroupApplication;

  @PostMapping(value = "/api/user-groups")
  public UserGroupResponse createUserGroup(@RequestBody UserGroupRequest userGroupRequest) {
    return userGroupConverter.toUserGroupResponse(
        userGroupApplication.createUserGroup(userGroupRequest));
  }

  @PostMapping(value = "/api/user-groups/{userGroupId}/users")
  public UserResponse createUser(@PathVariable Long userGroupId, @RequestBody UserRequest userRequest) {
    userRequest.setUserGroupId(userGroupId);
    return userConverter.toUserResponse(userApplication.createUser(userRequest));
  }

  @GetMapping(value = "/api/user-groups/{userGroupId}/users/{userId}/permissions")
  public List<Permission> getPermission(@PathVariable Long userGroupId, @PathVariable Long userId) {
    return userApplication.getUserByUserIdAndUserGroupId(userId, userGroupId)
        .getAllPermissions();
  }

}