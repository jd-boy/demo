package org.jd.demo.maxwell.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jd.demo.maxwell.message.MaxwellMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DdlMessageHandler implements MessageHandler {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void handler(MaxwellMessage message) {
    if (!message.isDdl()) {
      return;
    }
    String sql = message.getSql();
    sql = sql.replace('`' + message.getDatabase() + '`' + '.', "");
    log.info("Execute dml: {}", sql);
    jdbcTemplate.execute(sql);
  }

}