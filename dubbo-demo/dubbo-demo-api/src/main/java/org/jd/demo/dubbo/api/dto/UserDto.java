package org.jd.demo.dubbo.api.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * @Auther jd
 */
@Data
@Builder
public class UserDto implements Serializable {

  private Integer id;

  private String name;

}
