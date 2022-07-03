package com.jz.demo.vertx;

import org.jd.zeus.vertx.springboot.autoconfiguration.EnableVertxWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableVertxWeb
@SpringBootApplication
public class VertxApplication {

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class, args);
    }

}
