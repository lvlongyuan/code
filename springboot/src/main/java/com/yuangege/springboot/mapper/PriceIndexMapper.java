package com.yuangege.springboot.mapper;

import com.yuangege.springboot.entity.PriceIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PriceIndexMapper {

    //查询所有
    @Select("select * from sys_priceindex")
    List<PriceIndex> findAll();

    //分页查询
    @Select("select  * from sys_priceindex where datename like #{datename} limit #{pageNum},#{pageSize}")
    List<PriceIndex> seletPage(Integer pageNum, Integer pageSize, String datename);

    //数据总数 total
    @Select("select count(*) from sys_priceindex")
    Integer selectTotal( String datename);
}
