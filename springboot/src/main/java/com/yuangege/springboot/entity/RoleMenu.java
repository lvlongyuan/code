package com.yuangege.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_rolemenu")
public class RoleMenu {

    private Integer roleId;
    private Integer menuId;
}
