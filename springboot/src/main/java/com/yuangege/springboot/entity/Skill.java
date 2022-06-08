package com.yuangege.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_zzjs")
public class Skill {
    private String plantName;
    private String plantUrl;
    private String imgUrl;
}
