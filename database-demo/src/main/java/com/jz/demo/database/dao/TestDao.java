package com.jz.demo.database.dao;

import com.jz.demo.database.po.PeoplePo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TestDao {

    @Select("select name from people where id = #{id}")
    String selectName(Integer id);

    @Update("update people set name = #{name} where id = #{id}")
    void updateNameById(@Param("name") String name, @Param("id") Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into people (name) values (#{po.name})")
    void insert(@Param("po") PeoplePo po);

}
