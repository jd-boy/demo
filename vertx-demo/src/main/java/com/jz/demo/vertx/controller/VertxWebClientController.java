package com.jz.demo.vertx.controller;

import com.jz.demo.vertx.config.VertxConfiguration;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vertx/client")
public class VertxWebClientController {

    private WebClient webClient = WebClient.create(VertxConfiguration.vertx,
            new WebClientOptions()
                    .setKeepAlive(true));

    @GetMapping(value = "/test")
    public String test(@RequestParam int timeout) throws InterruptedException {
        System.out.println("test   " + System.currentTimeMillis());
        long time = System.currentTimeMillis();
        webClient.get(8080, "127.0.0.1", "/vertx/client/test1?timeout=" + timeout)
                .timeout(10)
                .send()
                .onSuccess(response -> {
                    System.out.println(response.body().toString());
                })
                .onFailure(err -> {
                    System.out.println("error:  " + (System.currentTimeMillis() - time));
                });
        Thread.sleep(5);
        return "发出请求";
    }

    @GetMapping(value = "/test1")
    public String test1(@RequestParam int timeout) throws InterruptedException {
//        Thread.sleep(timeout);
        System.out.println(System.currentTimeMillis());
        return "成功";
    }

}
