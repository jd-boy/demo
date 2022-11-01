package org.jd.demo.maxwell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

/**
 * @Auther jd
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableFieldInfo implements RowMapper<TableFieldInfo> {

  private String field;

  private String originType;

  private ColumnType type;

  private Boolean allowNull;

  private String key;

  private String defaultValue;

  private String extra;

  public boolean isBinaryType() {
    return type.isBinary();
  }

  @Override
  public TableFieldInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
    String type = rs.getString("Type");
    ColumnType columnType = null;
    if (Objects.nonNull(type)) {
      int index = type.indexOf('(');
      if (index < 0) {
        columnType = ColumnType.valueOf(type.toUpperCase());
      } else {
        columnType = ColumnType.valueOf(type.substring(0, type.indexOf('(')).toUpperCase());
      }
    }
    return TableFieldInfo.builder()
        .field(rs.getString("Field"))
        .originType(type)
        .type(columnType)
        .allowNull(rs.getBoolean("Null"))
        .key(rs.getString("Key"))
        .defaultValue(rs.getString("Default"))
        .extra(rs.getString("Extra"))
        .build();
  }

}
