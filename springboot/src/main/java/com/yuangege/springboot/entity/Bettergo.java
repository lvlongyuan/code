package com.yuangege.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.sql.Date;
@Data
@TableName("sys_bettergo")
public class Bettergo {
    private String move;
    private String goOut;
    private String openWin;
    private Date datatime;
}
