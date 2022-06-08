package com.yuangege.springboot.controller;

import com.yuangege.springboot.entity.Skill;
import com.yuangege.springboot.mapper.SkillMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Resource
    SkillMapper skillMapper;

    @GetMapping
    public List<Skill> findAll(){
        return skillMapper.findAll();
    }

}
