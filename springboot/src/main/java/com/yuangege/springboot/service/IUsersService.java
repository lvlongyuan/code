package com.yuangege.springboot.service;

import com.yuangege.springboot.controller.dto.UserDTO;
import com.yuangege.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 元哥哥
 * @since 2022-04-03
 */
public interface IUsersService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);
}
