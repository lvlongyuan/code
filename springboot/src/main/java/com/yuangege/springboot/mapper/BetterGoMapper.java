package com.yuangege.springboot.mapper;

import com.yuangege.springboot.entity.Bettergo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BetterGoMapper {

    @Select("select * from sys_bettergo")
    List<Bettergo> findAll();
}
