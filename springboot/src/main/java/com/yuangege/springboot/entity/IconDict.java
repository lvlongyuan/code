package com.yuangege.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_icondict")
public class IconDict {
    private String name;
    private String value;
    private String type;
}
