package org.jd.demo.jpa.application;

import org.jd.demo.jpa.domain.user.User;
import org.jd.demo.jpa.domain.user.repository.UserRepository;
import org.jd.demo.jpa.domain.usergroup.UserGroup;
import org.jd.demo.jpa.domain.usergroup.repository.UserGroupRepository;
import org.jd.demo.jpa.pojo.request.UserGroupRequest;
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
