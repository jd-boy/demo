package org.jd.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Slf4j
@Component
public class MessageListener {

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "${config.rabbitmq.queue}"),
      exchange = @Exchange(value = "${config.rabbitmq.exchange}", type = ExchangeTypes.TOPIC),
      key = "${config.rabbitmq.routingKey}"
  ))
  @SneakyThrows
  public void process(Channel channel, String msg) {
    log.info("接收到消息：{}", msg);
    Thread.sleep(5_000);
    throw new RuntimeException("测试异常");
  }

}
