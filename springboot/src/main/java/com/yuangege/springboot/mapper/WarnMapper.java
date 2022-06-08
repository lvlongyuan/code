package com.yuangege.springboot.mapper;

import com.yuangege.springboot.entity.Warn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WarnMapper{

    //查询所有
    @Select("select * from sys_warn")
    List<Warn> findAll();

    //分页查询
    @Select("select  * from sys_warn where warnName like #{warnName} limit #{pageNum},#{pageSize}")
    List<Warn> seletPage(Integer pageNum, Integer pageSize, String warnName);

    //数据总数 total
    @Select("select count(*) from sys_warn")
    Integer selectTotal( String warnName);
}
