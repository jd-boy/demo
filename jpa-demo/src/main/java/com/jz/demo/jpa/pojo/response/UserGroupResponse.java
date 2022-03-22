package com.jz.demo.jpa.pojo.response;

import lombok.Data;

/**
 * @Auther jd
 */
@Data
public class UserGroupResponse {

  private Long id;

  private String name;

  private IdNameResponse creator;

}
