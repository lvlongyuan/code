package com.yuangege.springboot.controller.dto;

import com.yuangege.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 接受前端登录请求返回的参数
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String token;
    private String role;
    private List<Menu> menus;
}
