package com.yuangege.springboot.controller;

import com.yuangege.springboot.entity.Position;
import com.yuangege.springboot.entity.Weather;
import com.yuangege.springboot.mapper.WeatherMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Resource
    public WeatherMapper weatherMapper;

    @GetMapping
    public List<Weather> findAll(){
        return weatherMapper.findAll();
    }
}
