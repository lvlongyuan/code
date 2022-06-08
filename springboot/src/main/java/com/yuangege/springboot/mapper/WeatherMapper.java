package com.yuangege.springboot.mapper;

import com.yuangege.springboot.entity.Weather;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WeatherMapper {
    @Select("select * from sys_weather")
    List<Weather> findAll();
}
