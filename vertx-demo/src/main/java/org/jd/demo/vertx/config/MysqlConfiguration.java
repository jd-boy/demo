package org.jd.demo.vertx.config;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther jd
 */
@Setter
@Configuration
@ConfigurationProperties("vertx.datasource")
public class MysqlConfiguration {

  private String host;

  private int port;

  private String database;

  private String user;

  private String password;

  private int poolMaxSize;

  private int connectTimeout;

  @Bean
  MySQLPool jdbcPool(Vertx vertx) {
    return MySQLPool.pool(vertx,
        new MySQLConnectOptions()
            .setConnectTimeout(connectTimeout)
            .setHost(host)
            .setPort(port)
            .setDatabase(database)
            .setUser(user)
            .setPassword(password),
        new PoolOptions()
            .setMaxSize(poolMaxSize));
  }

}
