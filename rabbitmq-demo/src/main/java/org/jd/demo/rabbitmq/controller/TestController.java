package org.jd.demo.rabbitmq.controller;

import org.jd.demo.rabbitmq.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

  private final MessageProducer messageProducer;

  @Value("${config.rabbitmq.routingKey}")
  private String routingKey;

  @GetMapping(value = "/api/rabbitmq/ack-test")
  public Object ackTest() {
    messageProducer.send(routingKey, "测试消息");
    return "OK";
  }

}
