package com.yuangege.springboot.mapper;

import com.yuangege.springboot.entity.Market;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MarketMapper {

    @Select("select * from sys_market")
    List<Market> findAll();

    //分页查询
    @Select("select  * from sys_market where name like #{name} limit #{pageNum},#{pageSize}")
    List<Market> seletPage(Integer pageNum, Integer pageSize, String name);

    //数据总数 total
    @Select("select count(*) from sys_market")
    Integer selectTotal( String name);
}
