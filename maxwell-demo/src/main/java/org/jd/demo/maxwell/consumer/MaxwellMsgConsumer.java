package org.jd.demo.maxwell.consumer;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jd.demo.maxwell.handler.DdlMessageHandler;
import org.jd.demo.maxwell.handler.DmlMessageHandler;
import org.jd.demo.maxwell.message.MaxwellMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MaxwellMsgConsumer {

  private final DmlMessageHandler dmlMessageHandler;

  private final DdlMessageHandler ddlMessageHandler;

  @SneakyThrows
  @KafkaListener(topics = "maxwell")
  public void consumer(ConsumerRecord<String, String> record) {
    String message = record.value();
    log.info("收到消息:{}", message);
    message = message.replaceAll("`", "\\`");
    var msg = JSON.parseObject(message, MaxwellMessage.class);
    if (msg.isDml()) {
      dmlMessageHandler.handler(msg);
    } else if (msg.isDdl()) {
      ddlMessageHandler.handler(msg);
    }
  }

}
