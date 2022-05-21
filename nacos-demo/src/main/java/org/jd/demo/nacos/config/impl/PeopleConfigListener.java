package org.jd.demo.nacos.config.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jd.demo.nacos.config.ConfigListener;
import org.jd.demo.nacos.config.NacosConfigListener;
import org.jd.demo.nacos.dto.PeopleDto;

/**
 * @Auther jd
 */
@Slf4j
@NacosConfigListener(
    name = "people-config",
    group = "NACOS_GROUP",
    dataId = "people-config.json")
public class PeopleConfigListener implements ConfigListener<List<PeopleDto>> {

  @Override
  public void loadConfig(List<PeopleDto> config) {
    log.info("接收到People配置：{}", config);
  }

}