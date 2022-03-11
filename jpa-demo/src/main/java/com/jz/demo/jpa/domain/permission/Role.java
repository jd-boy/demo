package com.jz.demo.jpa.domain.permission;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @Auther jd
 */
@Data
@Entity
@Table(name = "cfg_role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`key`", unique = true, nullable = false)
  private String key;

  @Column(nullable = false)
  private String name;

}
