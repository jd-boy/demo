package org.jd.demo.springcloud.api.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther jd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

  private Integer id;

  private String name;

}
