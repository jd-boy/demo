package org.jd.demo.nacos.controller;

import com.alibaba.nacos.api.config.ConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jd.demo.nacos.config.NacosAutoLoader;
import org.jd.demo.nacos.dto.PeopleDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@RestController
@RequiredArgsConstructor
public class TestController {

  private final ConfigService configService;

  private final NacosAutoLoader nacosAutoLoader;

  private final ObjectMapper objectMapper;

  @GetMapping(value = "/api/nacos/configs")
  public List<PeopleDto> getConfigs() {
    return nacosAutoLoader.getConfig("people-config");
  }

  @SneakyThrows
  @GetMapping(value = "/api/nacos/configs/update")
  public String updateConfig(@RequestParam String name) {
    List<PeopleDto> peopleDtos = Arrays.asList(new PeopleDto(1, name));
    configService.publishConfig("people-config.json", "NACOS_GROUP",
        objectMapper.writeValueAsString(peopleDtos));
    return "OK";
  }

}