package com.yuangege.springboot.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@TableName(value = "sys_users")//指定数据库表的名字，否则将按照类名小写user进行查询
@ToString
public class User {
    @ApiModelProperty("id")
    @TableId(value = "id",type = IdType.AUTO)//实体类中的定义可以与数据库中有差别 只需要标注这个
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    @JsonIgnore //控制台不展示password
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("创建时间")
        @TableField(value = "create_time")
    private Date createTime;

    @ApiModelProperty("头像")
        @TableField(value = "avatar_url")//制定数据库的字段名称
    private String avatar;

    @ApiModelProperty("角色")
    private String role;
}
