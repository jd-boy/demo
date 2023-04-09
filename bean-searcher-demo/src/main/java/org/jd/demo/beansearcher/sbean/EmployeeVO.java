package org.jd.demo.beansearcher.sbean;

import cn.zhxu.bs.bean.DbField;
import cn.zhxu.bs.bean.SearchBean;
import cn.zhxu.bs.operator.Contain;
import cn.zhxu.bs.operator.StartWith;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jd.demo.beansearcher.enums.Gender;

import java.time.LocalDateTime;

/**
 * 带冗余后缀的实体类
 */
@SearchBean(tables = "employee")
public class EmployeeVO extends BaseBean {

  @DbField(onlyOn = { StartWith.class, Contain.class })
  private String name;

  private Integer age;

  private Gender gender;

  @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
  private LocalDateTime entryDate;

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public Gender getGender() {
    return gender;
  }

  public LocalDateTime getEntryDate() {
    return entryDate;
  }

}
