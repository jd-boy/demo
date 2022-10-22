package org.jd.demo.jpa.domain.permission;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 * @Auther jd
 */
@Data
@Entity
@Table(name = "def_role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(length = 20)
  private Long id;

  @Column(name = "`key`", unique = true, nullable = false)
  private String key;

  @Column(nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(name = "rel_role_permission",
      uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}),
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private List<Permission> permissions;

}
