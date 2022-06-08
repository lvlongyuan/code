package com.yuangege.springboot.mapper;

import com.yuangege.springboot.entity.Qua;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuaMapper {

    @Select("select * from sys_qua")
    List<Qua> findAll();
}
