package org.jd.demo.nacos.config;

import lombok.Data;

/**
 * @Auther jd
 */
@Data
public class NacosConfigInfo {

  private String name;

  private String group;

  private String dataId;

  private String className;

  private Boolean isList = false;

}
