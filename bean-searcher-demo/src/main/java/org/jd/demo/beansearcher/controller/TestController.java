package org.jd.demo.beansearcher.controller;

import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.MapSearcher;
import cn.zhxu.bs.util.MapUtils;
import lombok.RequiredArgsConstructor;
import org.jd.demo.beansearcher.sbean.DFieldBean;
import org.jd.demo.beansearcher.sbean.DTableBean;
import org.jd.demo.beansearcher.sbean.EmployeeVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/beansearcher")
public class TestController {

  private final MapSearcher mapSearcher;

  private final BeanSearcher beanSearcher;

  @GetMapping("/dfield")
  public Object dynamicField() {
    Map<String, Object> map = new HashMap<>();
    // 动态指定查询的字段
    map.put("fieldName", "name");
    return mapSearcher.searchList(DFieldBean.class, map);
  }

  @GetMapping("/dynamic-table")
  public Object dynamicTable() {
    Map<String, Object> map = new HashMap<>();
    // 动态指定查询的表名
    map.put("table", "department");
    return mapSearcher.searchList(DTableBean.class, map);
  }

  @GetMapping("/index")
  public Object index() {
    return beanSearcher.searchList(EmployeeVO.class,
          MapUtils.builder()
                .field(EmployeeVO::getName, "Troy")
                .build()
          );
  }


}
