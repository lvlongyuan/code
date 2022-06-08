package com.yuangege.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuangege.springboot.common.Result;
import com.yuangege.springboot.entity.Role;
import com.yuangege.springboot.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 元哥哥
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Role role){
        return Result.success(roleService.saveOrUpdate(role));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }

    //查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam (defaultValue = "")String name

    ) {
        IPage<Role> page = new Page<>(pageNum, pageSize);//page内传入两个参数 User泛型
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();//定义queryWrapper User泛型

        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(page, queryWrapper));
    }


    @PostMapping("/rolemenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId,menuIds);
        return Result.success();
    }

    @GetMapping("/rolemenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return  Result.success(roleService.getRoleMenu(roleId));
    }

}

