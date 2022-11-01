package org.jd.demo.maxwell.handler;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.jd.demo.maxwell.message.MaxwellMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @Auther jd
 */
@Slf4j
@Component
public class DmlMessageHandler extends AbstractMessageHandler {

  private static final String INSERT_SQL = "INSERT INTO %s (%s) VALUES (%s);";
  private static final String UPDATE_SQL = "UPDATE %s SET %s WHERE %s;";
  private static final String DELETE_SQL = "DELETE FROM %s WHERE %s;";

  public DmlMessageHandler(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  public void handler(MaxwellMessage message) {
    if (!message.isDml()) {
      return;
    }
    if (message.isInsert()) {
      handleInsertSql(message);
    } else if (message.isUpdate()) {
      handleUpdateSql(message);
    } else if (message.isDelete()) {
      handleDeleteSql(message);
    } else {
      throw new RuntimeException("Invalid dml operate " + message.getType());
    }
  }

  private void handleUpdateSql(MaxwellMessage message) {
    String tableName = message.getTable();
    Set<String> oldColumns = message.getOld().keySet();
    Set<String> sqlColumns = new HashSet<>(oldColumns);
    Set<String> nullColumnNames = new HashSet<>();
    List<Object> values = new ArrayList<>();
    // 先添加 set 的值
    for (String columnName : oldColumns) {
      Object value = message.getData().get(columnName);
      if (isBinaryType(tableName, columnName)) {
        value = convertBinaryTypeValue(value);
      }
      values.add(value);
    }
    // 添加 where 条件
    for (String columnName : oldColumns) {
      Object value = message.getOld().get(columnName);
      if (isBinaryType(tableName, columnName)) {
        value = convertBinaryTypeValue(value);
      }
      if (Objects.isNull(value)) {
        nullColumnNames.add(columnName);
      } else {
        values.add(value);
      }
    }
    for (Entry<String, Object> entry : message.getData().entrySet()) {
      String columnName = entry.getKey();
      if (oldColumns.contains(columnName)) {
        continue;
      }
      sqlColumns.add(columnName);
      Object value = entry.getValue();
      if (isBinaryType(tableName, columnName)) {
        value = convertBinaryTypeValue(value);
      }
      if (Objects.isNull(value)) {
        nullColumnNames.add(columnName);
      } else {
        values.add(value);
      }
    }
    String updateSql = String.format(UPDATE_SQL, message.getTable(),
        preparedStatementSet(oldColumns),
        preparedStatementWhere(sqlColumns, nullColumnNames));
    jdbcTemplate.update(updateSql, values.toArray());
  }

  private void handleDeleteSql(MaxwellMessage message) {
    String tableName = message.getTable();
    int size = message.getData().size();
    List<Object> values = new ArrayList<>(size);
    Set<String> columnNames = message.getData().keySet();
    Set<String> nullColumnNames = new HashSet<>();
    for (String columnName : columnNames) {
      Object value = message.getData().get(columnName);
      if (isBinaryType(tableName, columnName)) {
        value = convertBinaryTypeValue(value);
      }
      if (Objects.isNull(value)) {
        nullColumnNames.add(columnName);
      } else {
        values.add(value);
      }
    }
    String delSql = String.format(DELETE_SQL, message.getTable(),
        preparedStatementWhere(columnNames, nullColumnNames));
    jdbcTemplate.update(delSql, values.toArray());
  }

  private void handleInsertSql(MaxwellMessage message) {
    StringBuilder columns = new StringBuilder();
    StringBuilder valueStatement = new StringBuilder();
    List<Object> values = new ArrayList<>();
    String tableName = message.getTable();
    boolean first = true;
    for (Entry<String, Object> entry : message.getData().entrySet()) {
      String column = entry.getKey();
      Object value = entry.getValue();
      if (first) {
        first = false;
      } else {
        columns.append(',');
        valueStatement.append(',');
      }
      columns.append(column);
      valueStatement.append('?');
      if (isBinaryType(tableName, column)) {
        value = convertBinaryTypeValue(value);
      }
      values.add(value);
    }
    String insertSql = String.format(INSERT_SQL, message.getTable(), columns, valueStatement);
    jdbcTemplate.update(insertSql, values.toArray());
  }

  private byte[] convertBinaryTypeValue(Object value) {
    if (Objects.isNull(value)) {
      return null;
    }
    return Base64.getDecoder().decode(value.toString());
  }

  /**
   * 生成 where 条件的预处理sql
   * @param columnNames        包含全部需要生成 where 条件的列名
   * @param nullColumnNames    该字段保存的是值为空的列名，该字段的内容应为 columnNames 的子集
   * @return
   */
  private String preparedStatementWhere(Collection<String> columnNames,
      Collection<String> nullColumnNames) {
    if (CollectionUtils.isEmpty(columnNames)) {
      return "";
    }
    StringBuilder where = new StringBuilder();
    boolean first = true;
    for (String column : columnNames) {
      if (first) {
        first = false;
      } else {
        where.append(" AND ");
      }
      where.append(column);
      if (Objects.nonNull(nullColumnNames) && nullColumnNames.contains(column)) {
        where.append(" is null");
      } else {
        where.append('=').append('?');
      }
    }
    return where.toString();
  }

  /**
   * 生产 udpate 语句的预处理 set 相关语句
   * @param columnNames   列名
   * @return    column0=?,column1=?
   */
  private String preparedStatementSet(Collection<String> columnNames) {
    if (CollectionUtils.isEmpty(columnNames)) {
      return "";
    }
    StringBuilder set = new StringBuilder();
    boolean first = true;
    for (String column : columnNames) {
      if (first) {
        first = false;
      } else {
        set.append(',');
      }
      set.append(column).append('=').append('?');
    }
    return set.toString();
  }

}
