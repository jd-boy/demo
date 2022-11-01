package org.jd.demo.maxwell.message;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @Auther jd
 */
@Data
public class MaxwellMessage {

  private String database;

  private String table;

  private String type;

  private Long ts;

  private int xid;

  private Boolean commit;

  private JSONObject data;

  private JSONObject old;

  private JSONObject def;

  private String sql;

  public boolean isInsert() {
    if (!StringUtils.hasText(type)) {
      return false;
    }
    if (type.equalsIgnoreCase("insert")) {
      return true;
    }
    return false;
  }

  public boolean isDelete() {
    if (!StringUtils.hasText(type)) {
      return false;
    }
    if (type.equalsIgnoreCase("delete")) {
      return true;
    }
    return false;
  }

  public boolean isUpdate() {
    if (!StringUtils.hasText(type)) {
      return false;
    }
    if (type.equalsIgnoreCase("update")) {
      return true;
    }
    return false;
  }

  public boolean isDml() {
    if (isInsert() || isUpdate() || isDelete()) {
      return true;
    }
    return false;
  }

  public boolean isDdl() {
    if (!StringUtils.hasText(type)) {
      return false;
    }
    type = type.toLowerCase();
    switch (type) {
      case "database-create":
      case "database-alter":
      case "database-drop":
      case "table-alter":
      case "table-drop":
      case "table-create": return true;
    }
    return false;
  }

}
