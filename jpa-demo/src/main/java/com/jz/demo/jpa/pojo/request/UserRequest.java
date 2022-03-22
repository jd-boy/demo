package com.jz.demo.jpa.pojo.request;

import java.util.List;
import lombok.Data;

/**
 * @Auther jd
 */
@Data
public class UserRequest {

  private String name;

  private String email;

  private String password;

  private Long userGroupId;

  private List<Long> roleIds;

}
