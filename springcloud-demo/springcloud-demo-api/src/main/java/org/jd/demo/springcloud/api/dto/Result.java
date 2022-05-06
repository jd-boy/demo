package org.jd.demo.springcloud.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Auther jd
 */
@Data
@AllArgsConstructor
public class Result {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAIL = "FAIL";

  private String code;

  private String message;

  private Object data;

  public static Result fail(String message) {
    return new Result(FAIL, message, null);
  }

  public static Result success(Object data) {
    return new Result(SUCCESS, null, data);
  }

}
