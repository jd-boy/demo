package org.jd.demo.jpa.domain.permission;

import org.jd.demo.jpa.domain.enumeration.RequestMethod;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "cfg_interface")
public class Interface {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RequestMethod method;

  @Column(nullable = false)
  private String path;

  @Column
  private String description;

}
