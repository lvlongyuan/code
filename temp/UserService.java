package com.yuangege.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuangege.springboot.controller.dto.UserDTO;
import com.yuangege.springboot.entity.User;
import com.yuangege.springboot.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {

//    @Autowired
//    private UserMapper userMapper;//引进了userMapper，可以使用UserMapper内的内容
//
//    public int save(User user){
//        if(user.getId() == null){ //如果user有id，则表示新增
//            return userMapper.insert(user);
//        }else { //否则表示更新
//            return userMapper.update(user);
//        }
//
//    }


    public boolean saveUser(User user) {
        return saveOrUpdate(user);
    }
}
