package org.jd.demo.jpa.config;

import org.jd.demo.jpa.domain.permission.Permission;
import org.jd.demo.jpa.domain.user.User;
import org.jd.demo.jpa.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Auther jd
 */
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    if (Objects.isNull(email)) {
      return null;
    }
    User example = new User();
    example.setEmail(email);
    Optional<User> user = userRepository.findOne(Example.of(example));
    if (user.isEmpty()) {
      throw new UsernameNotFoundException(email + " 不存在");
    }
    return toUserDetailImpl(user.get());
  }

  private DefaultUserDetail toUserDetailImpl(User user) {
    List<GrantedAuthority> grantedAuthorities = user.getAllPermissions().stream()
        .map(Permission::getKey)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    return DefaultUserDetail.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .password(user.getPassword())
        .grantedAuthorities(grantedAuthorities)
        .build();
  }

}