package com.jz.demo.jpa.domain.user;

import com.jz.demo.jpa.constant.DDLConstants;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * @Auther jd
 */
@Data
@Entity
public class UserGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "creator_id")
  private User creator;

  @Column(columnDefinition = DDLConstants.DATETIME_DEFAULT_CURRENT_TIMESTAMP, nullable = false)
  private LocalDateTime updateTime;

  @Column(columnDefinition = DDLConstants.DATETIME_DEFAULT_CURRENT_TIMESTAMP, nullable = false)
  private LocalDateTime createTime;

}