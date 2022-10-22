package org.jd.demo.database.po;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther jd
 */
@Data
@NoArgsConstructor
public class PeoplePo {

  private Integer id;

  private String name;

  public PeoplePo(String name) {
    this.name = name;
  }

}
