package com.yuangege.springboot.controller;

import com.yuangege.springboot.entity.Bettergo;
import com.yuangege.springboot.mapper.BetterGoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/better")
public class BetterGoController {
    @Resource
    BetterGoMapper betterGoMapper;

    @GetMapping
    public List<Bettergo> findAll(){
        return betterGoMapper.findAll();
    }
}
