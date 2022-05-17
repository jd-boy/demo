package org.jd.demo.nacos.config;

/**
 * @Auther jd
 */
public interface ConfigLoader<T> {

  void loadConfig(T config);

}
