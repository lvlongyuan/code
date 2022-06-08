package com.yuangege.springboot.service;

import com.yuangege.springboot.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 元哥哥
 * @since 2022-04-30
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
