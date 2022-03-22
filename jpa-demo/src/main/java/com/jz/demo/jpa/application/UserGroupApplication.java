package com.jz.demo.jpa.application;

import com.jz.demo.jpa.domain.user.User;
import com.jz.demo.jpa.domain.user.repository.UserRepository;
import com.jz.demo.jpa.domain.usergroup.UserGroup;
import com.jz.demo.jpa.domain.usergroup.repository.UserGroupRepository;
import com.jz.demo.jpa.pojo.request.UserGroupRequest;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
@RequiredArgsConstructor
public class UserGroupApplication {

  private final UserRepository userRepository;

  private final UserGroupRepository userGroupRepository;

  @Transactional
  public UserGroup createUserGroup(UserGroupRequest userGroupRequest) {
    Optional<User> user = userRepository.findById(userGroupRequest.getCreatorId());
    if (user.isEmpty()) {
      throw new IllegalArgumentException("creatorId 不存在");
    }
    UserGroup userGroup = new UserGroup();
    userGroup.setName(userGroupRequest.getName());
    userGroup.setCreator(user.get());
    return userGroupRepository.save(userGroup);
  }

}
