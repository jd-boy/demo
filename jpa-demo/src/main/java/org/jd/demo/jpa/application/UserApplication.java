package org.jd.demo.jpa.application;

import org.jd.demo.jpa.domain.user.User;
import org.jd.demo.jpa.domain.user.repository.UserRepository;
import org.jd.demo.jpa.domain.usergroup.UserGroup;
import org.jd.demo.jpa.domain.usergroup.repository.UserGroupRepository;
import org.jd.demo.jpa.pojo.request.UserRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
@RequiredArgsConstructor
public class UserApplication {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final UserGroupRepository userGroupRepository;

  public User getUserByUserIdAndUserGroupId(Long userId, Long userGroupId) {
    User user = new User();
    user.setId(userId);
    user.setUserGroup(new UserGroup(userGroupId));
    Optional<User> result = userRepository.findOne(Example.of(user));
    if (result.isEmpty()) {
      throw new IllegalArgumentException("用户不存在");
    }
    return result.get();
  }

  public User createUser(UserRequest userRequest) {
    Optional<UserGroup> userGroup = userGroupRepository.findById(userRequest.getUserGroupId());
    if (userGroup.isEmpty()) {
      throw new IllegalArgumentException("userGroupId 不存在");
    }
    User user = new User();
    user.setName(userRequest.getName());
    user.setEmail(userRequest.getEmail());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setUserGroup(userGroup.get());
    return userRepository.save(user);
  }

}
