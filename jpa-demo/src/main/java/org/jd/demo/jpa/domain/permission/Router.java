package org.jd.demo.jpa.domain.permission;

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
@Table(name = "cfg_router")
public class Router {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String routing;

  @Column
  private String description;

}
