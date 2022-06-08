package com.yuangege.springboot.mapper;

import com.yuangege.springboot.entity.Skill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SkillMapper {

    @Select("select * from sys_zzjs")
    List<Skill> findAll();

}
