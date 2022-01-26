package com.jz.demo.vertx;

import com.jz.demo.vertx.core.EnableVertxWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableVertxWeb
@SpringBootApplication
public class VertxApplication {

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class, args);
    }

}
