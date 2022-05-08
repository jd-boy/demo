package org.jd.demo.nacos.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Auther jd
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "config")
@RequiredArgsConstructor
public class NacosAutoConfig {

  private final static Executor EXECUTOR = Executors.newSingleThreadExecutor(r -> new Thread(r, "nacos-config-refresh"));

  private final ObjectMapper objectMapper;

  private final NacosServiceInfo nacosServiceInfo;

  private ConfigService configService;

  @Getter
  @Setter
  private List<NacosConfigInfo> nacosConfigInfos = new ArrayList<>();

  private Map<String, Object> configInfoMap;

  public <T> T getConfig(String configName) {
    return (T) configInfoMap.get(configName);
  }

  @PostConstruct
  private void init() throws NacosException {
    Properties properties = new Properties();
    properties.put(PropertyKeyConst.SERVER_ADDR, nacosServiceInfo.getServerAddr());
    properties.put(PropertyKeyConst.USERNAME, nacosServiceInfo.getUsername());
    properties.put(PropertyKeyConst.PASSWORD, nacosServiceInfo.getPassword());
    properties.put(PropertyKeyConst.NAMESPACE, nacosServiceInfo.getNamespace());
    configService = NacosFactory.createConfigService(properties);

    this.configInfoMap = new HashMap<>(nacosConfigInfos.size());
    for (NacosConfigInfo nacosConfigInfo : nacosConfigInfos) {
      addListener(nacosConfigInfo);
    }
  }

  @SneakyThrows
  private void addListener(NacosConfigInfo configInfo) {
    configService.addListener(configInfo.getDataId(), configInfo.getGroup(),
        new NacosConfigListener(configInfo));
  }

  private class NacosConfigListener implements Listener {

    private final NacosConfigInfo nacosConfigInfo;

    private final Class<?> clazz;

    @SneakyThrows
    public NacosConfigListener(NacosConfigInfo nacosConfigInfo) {
      this.nacosConfigInfo = nacosConfigInfo;
      this.clazz = Class.forName(nacosConfigInfo.getClassName());
    }

    @Override
    public Executor getExecutor() {
      return EXECUTOR;
    }

    @Override
    @SneakyThrows
    public void receiveConfigInfo(String configInfo) {
      log.info("group：{} dataId：{} receive config：{}", nacosConfigInfo.getGroup(),
          nacosConfigInfo.getDataId(), configInfo);
      String key = StringUtils.hasText(nacosConfigInfo.getName()) ?
          nacosConfigInfo.getName() : nacosConfigInfo.getDataId();
      if (nacosConfigInfo.getIsList()) {
        configInfoMap.put(key, objectMapper.readValue(configInfo,
            objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)));
      } else {
        configInfoMap.put(key, objectMapper.convertValue(configInfo, clazz));
      }
    }
  }

}
