package org.jd.demo.nacos.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jd.demo.nacos.config.NacosAutoConfig;
import org.jd.demo.nacos.dto.PeopleDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@RestController
@RequiredArgsConstructor
public class TestController {

  private final NacosAutoConfig nacosAutoConfig;

  @GetMapping(value = "/api/nacos/configs")
  public List<PeopleDto> getConfigs() {
    return nacosAutoConfig.getConfig("people-config");
  }
}
