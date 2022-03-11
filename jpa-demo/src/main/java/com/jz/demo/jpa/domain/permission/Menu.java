package com.jz.demo.jpa.domain.permission;

import com.jz.demo.jpa.constant.DDLConstants;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 * @Auther jd
 */
@Data
@Entity
@Table(name = "cfg_menu")
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`key`", nullable = false, unique = true)
  private String key;

  @Column
  private String link;

  @Column(nullable = false)
  private String name;

  @Column
  private Integer priority;

  @Column
  private Long parentId;

  @Column(columnDefinition = DDLConstants.DATETIME_DEFAULT_CURRENT_TIMESTAMP, nullable = false)
  private LocalDateTime createTime;

}
