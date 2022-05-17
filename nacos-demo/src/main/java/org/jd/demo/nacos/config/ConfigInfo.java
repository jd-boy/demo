package org.jd.demo.nacos.config;

import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther jd
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigInfo {

  private String name;

  private String group;

  private String dataId;

  private Function<String, Object> converter;

}
