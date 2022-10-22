package org.jd.demo.jpa.domain.user;

import org.jd.demo.jpa.constant.DDLConstants;
import org.jd.demo.jpa.domain.permission.Permission;
import org.jd.demo.jpa.domain.permission.Role;
import org.jd.demo.jpa.domain.usergroup.UserGroup;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import org.springframework.util.CollectionUtils;

/**
 * @Auther jd
 */
@Data
@Entity
@Table(name = "def_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  @Column
  private String password;

  @ManyToOne
  @JoinColumn(name = "user_group_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private UserGroup userGroup;

  @ManyToMany
  @JoinTable(name = "rel_user_role", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}),
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<Role> roles;

  @ManyToMany
  @JoinTable(name = "cfg_rel_user_permission",
      uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "permission_id"}),
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private List<Permission> permissions;

  @Column(columnDefinition = DDLConstants.DATETIME_DEFAULT_CURRENT_TIMESTAMP, nullable = false)
  private LocalDateTime updateTime;

  @Column(columnDefinition = DDLConstants.DATETIME_DEFAULT_CURRENT_TIMESTAMP, nullable = false)
  private LocalDateTime createTime;

  public List<Permission> getAllPermissions() {
    List<Permission> allPermissions = new ArrayList<>();
    if (!CollectionUtils.isEmpty(roles)) {
      allPermissions.addAll(roles.stream()
          .map(Role::getPermissions)
          .flatMap(Collection::stream)
          .collect(Collectors.toList()));
    }
    if (!CollectionUtils.isEmpty(permissions)) {
      allPermissions.addAll(permissions);
    }
    return allPermissions.stream()
        .distinct().collect(Collectors.toList());
  }

}
