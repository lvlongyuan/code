package com.yuangege.springboot.controller;

import com.yuangege.springboot.entity.Qua;
import com.yuangege.springboot.mapper.QuaMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/qua")
public class QuaController {

    @Resource
    public QuaMapper quaMapper;

    @GetMapping
    public List<Qua> findAll(){
        return quaMapper.findAll();
    }


}
