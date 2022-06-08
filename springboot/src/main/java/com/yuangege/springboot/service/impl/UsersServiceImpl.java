package com.yuangege.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuangege.springboot.common.Constants;
import com.yuangege.springboot.controller.dto.UserDTO;
import com.yuangege.springboot.entity.Menu;
import com.yuangege.springboot.entity.User;
import com.yuangege.springboot.exception.ServiceException;
import com.yuangege.springboot.mapper.RoleMapper;
import com.yuangege.springboot.mapper.RoleMenuMapper;
import com.yuangege.springboot.mapper.UserMapper;
import com.yuangege.springboot.service.IMenuService;
import com.yuangege.springboot.service.IUsersService;
import com.yuangege.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 元哥哥
 * @since 2022-04-03
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UserMapper, User> implements IUsersService {

    private static final Log LOG = Log.get();

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    //登录
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null){
            BeanUtil.copyProperties(one,userDTO,true);
            //设置token
            String token = TokenUtils.getToken(one.getId().toString(),one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole();
            //设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    //注册
    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO,one,true);
            save(one);//把copy完的数据对象one存到数据库内
        }else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }

        return null;
    }

    //获取用户信息
    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try{
            one = getOne(queryWrapper);//从数据库查询用户信息

        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    private List<Menu> getRoleMenus(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        //当前角色所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        //查出系统所有菜单
        List<Menu> menus = menuService.findMenus("");
        //new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        //筛选当前用户角色的菜单
        for(Menu menu : menus){
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //removeIf（）移除children里面不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
