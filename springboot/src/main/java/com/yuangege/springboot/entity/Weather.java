package com.yuangege.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_weather")
public class Weather {

    private String sign;
    private String hdate;
    private String wea;
    private String tem;
    private String wind;
    private String qua;
    private String min;
    private String max;

}
