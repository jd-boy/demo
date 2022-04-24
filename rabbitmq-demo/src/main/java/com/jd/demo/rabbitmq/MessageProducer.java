package com.jd.demo.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Component
@RequiredArgsConstructor
public class MessageProducer {

  private final RabbitTemplate rabbitTemplate;

  @Value("${config.rabbitmq.exchange}")
  private String exchange;

  public void send(String routingKey, String msg) {
    rabbitTemplate.convertAndSend(exchange, routingKey, msg);
  }

}
