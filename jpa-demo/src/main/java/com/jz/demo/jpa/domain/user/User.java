package com.jz.demo.jpa.domain.user;

import com.jz.demo.jpa.constant.DDLConstants;
import com.jz.demo.jpa.domain.permission.Permission;
import com.jz.demo.jpa.domain.permission.Role;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 * @Auther jd
 */
@Data
@Entity
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
  @JoinColumn(name = "user_group_id", nullable = false)
  private UserGroup userGroup;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

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

}
