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
import lombok.EqualsAndHashCode;

/**
 * @Auther jd
 */
@Data
@Entity
@Table(name = "cfg_permission")
@EqualsAndHashCode(of = {"id", "key"})
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`key`", unique = true)
  private String key;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @ManyToMany
  @JoinTable(name = "cfg_rel_permission_interface",
      uniqueConstraints = @UniqueConstraint(columnNames = {"permission_id", "interface_id"}),
      joinColumns = @JoinColumn(name = "permission_id"),
      inverseJoinColumns = @JoinColumn(name = "interface_id")
  )
  private List<Interface> interfaces;

  @ManyToMany
  @JoinTable(name = "cfg_rel_permission_menu",
      uniqueConstraints = @UniqueConstraint(columnNames = {"permission_id", "menu_id"}),
      joinColumns = @JoinColumn(name = "permission_id"),
      inverseJoinColumns = @JoinColumn(name = "menu_id")
  )
  private List<Menu> menus;

  @ManyToMany
  @JoinTable(name = "cfg_rel_permission_router",
      uniqueConstraints = @UniqueConstraint(columnNames = {"permission_id", "router_id"}),
      joinColumns = @JoinColumn(name = "permission_id"),
      inverseJoinColumns = @JoinColumn(name = "router_id")
  )
  private List<Router> routers;

}