package org.jd.demo.maxwell.handler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jd.demo.maxwell.TableFieldInfo;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Auther jd
 */
public abstract class AbstractMessageHandler implements MessageHandler {

  private static final Map<String, Map<String, TableFieldInfo>> TABLE_INFO_MAP = new ConcurrentHashMap<>();

  protected final JdbcTemplate jdbcTemplate;

  public AbstractMessageHandler(JdbcTemplate jdbcTemplate) {
    Objects.requireNonNull(jdbcTemplate);
    this.jdbcTemplate = jdbcTemplate;
  }

  protected boolean isBinaryType(String tableName, String columnName) {
    Map<String, TableFieldInfo> tableInfo = getTableInfo(tableName);
    TableFieldInfo fieldInfo = tableInfo.get(columnName);
    if (Objects.isNull(fieldInfo)) {
      return false;
    }
    return fieldInfo.isBinaryType();
  }

  protected Map<String, TableFieldInfo> getTableInfo(String tableName) {
    return jdbcTemplate.query("DESC " + tableName, new TableFieldInfo())
        .stream().collect(Collectors.toMap(TableFieldInfo::getField, Function.identity()));
  }

}
