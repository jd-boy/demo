package org.jd.demo.nacos.config;

/**
 * @Auther jd
 */
public interface ConfigListener<T> {

  void loadConfig(T config);

}
