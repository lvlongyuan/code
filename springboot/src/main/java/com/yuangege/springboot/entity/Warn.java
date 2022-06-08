package com.yuangege.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_warn")
public class Warn {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String warnName;
    private String warnTime;
    private String warnUrl;
}
