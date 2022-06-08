package com.yuangege.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuangege.springboot.common.Constants;
import com.yuangege.springboot.common.Result;
import com.yuangege.springboot.entity.IconDict;
import com.yuangege.springboot.entity.Menu;
import com.yuangege.springboot.mapper.IconDictMapper;
import com.yuangege.springboot.service.IMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 元哥哥
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private IconDictMapper iconDictMapper;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Menu menu){
        return Result.success(menuService.saveOrUpdate(menu));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }

    //查询所有
    @GetMapping
    public Result findAll(@RequestParam (defaultValue = "")String name) {
        return Result.success( menuService.findMenus(name));
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }

    //根据id查询
    @GetMapping("/ids")
    public Result findAllIds() {
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam (defaultValue = "")String name
                               ){
        IPage<Menu> page = new Page<>(pageNum, pageSize);//page内传入两个参数 User泛型
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();//定义queryWrapper User泛型

        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(page, queryWrapper));
    }

    //图标查询
    @GetMapping("/icon")
    public Result getIcons() {
        QueryWrapper<IconDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.CODE_TYPE_ICON);
        return Result.success(iconDictMapper.selectList(queryWrapper));
    }
}

