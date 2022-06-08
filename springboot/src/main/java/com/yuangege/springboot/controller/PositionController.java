package com.yuangege.springboot.controller;

import com.yuangege.springboot.entity.Position;
import com.yuangege.springboot.mapper.PositionMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Resource
    public PositionMapper positionMapper;

    @GetMapping
    public List<Position> findAll(){
        return positionMapper.findAll();
    }
}
