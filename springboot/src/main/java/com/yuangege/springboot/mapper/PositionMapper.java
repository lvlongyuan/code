package com.yuangege.springboot.mapper;

import com.yuangege.springboot.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PositionMapper {

    @Select("select * from sys_position")
    List<Position> findAll();
}
