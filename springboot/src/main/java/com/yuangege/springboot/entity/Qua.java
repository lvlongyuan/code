package com.yuangege.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName("sys_qua")
public class Qua {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String quaName;
    private String quaIndex;
    private LocalDateTime datetime;

}
