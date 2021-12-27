package com.jz.demo.database.mysql;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestDao {

    @Select("select name from test where id = #{id}")
    String selectName(Integer id);

}
