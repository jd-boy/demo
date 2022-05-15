package com.jz.demo.vertx.controller;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/vertx/client")
@RequiredArgsConstructor
public class VertxWebClientController {

    private final Vertx vertx;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.create(vertx,
                new WebClientOptions()
                    .setMaxPoolSize(2)
                    .setKeepAlive(true));
    }

    @GetMapping(value = "/test")
    public String test(@RequestParam int timeout, @RequestParam int reqTimeout, @RequestParam String reqName) throws InterruptedException {
        log.info("进入开始请求：{}", reqName);
        long time = System.currentTimeMillis();
        webClient.get(8001, "127.0.0.1", "/api/vertx/client/test1?timeout=" + timeout)
                .timeout(reqTimeout)
                .send()
                .onSuccess(response -> {
                    log.info("{} 请求：{}", reqName, response.body().toString());
                })
                .onFailure(err -> {
                    log.info("{} 请求失败：{}", reqName, err.toString());
                });
        return "发出请求";
    }

    @GetMapping(value = "/test1")
    public String test1(@RequestParam int timeout) throws InterruptedException {
        Thread.sleep(timeout);
        return "成功";
    }

}
