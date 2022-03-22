package com.jz.demo.jpa.domain.usergroup;

import com.jz.demo.jpa.constant.DDLConstants;
import com.jz.demo.jpa.domain.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

/**
 * @Auther jd
 */
@Data
@Entity
@Table(name = "def_user_group")
@NoArgsConstructor
public class UserGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "creator_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private User creator;

  @Column(columnDefinition = DDLConstants.DATETIME_DEFAULT_CURRENT_TIMESTAMP,
      nullable = false, insertable = false)
  private LocalDateTime updateTime;

  @Column(columnDefinition = DDLConstants.DATETIME_DEFAULT_CURRENT_TIMESTAMP,
      nullable = false, insertable = false)
  private LocalDateTime createTime;

  public UserGroup(Long id) {
    Assert.notNull(id, "id cannot be null");
    this.id = id;
  }

}