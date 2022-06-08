package com.yuangege.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuangege.springboot.common.Constants;
import com.yuangege.springboot.common.Result;
import com.yuangege.springboot.controller.dto.UserDTO;
import com.yuangege.springboot.entity.User;
import com.yuangege.springboot.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/user")
public class Usercontroller {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired//引入 userService 其中继承了UserMapper
    private UsersServiceImpl userService;

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) ){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        System.out.println(username);
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) ){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    //@RequestBody可以将前端传回的josn数据转换为java对象
    //新增或者更新都在userService的save方法中
    @PostMapping
    public Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }

    //查询所有
    @GetMapping
    public Result findAll(){
//        return userMapper.findAll();
        return Result.success(userService.list());
    }

    //请求用户个人信息
    @GetMapping("/username/{username}")
    public Result findPerson(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    //删除数据
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){ //PathVariable URL地址  id
//       return userMapper.deleteById(id);
        return Result.success(userService.removeById(id));
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeBatchByIds(ids));
    }

//    //分页查询
//    //接口路基：/user/page
//    //@RequestParam接受 ?pageNum=1&pageSize=10
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum,
//                                        @RequestParam Integer pageSize,
//                                        @RequestParam String username,
//                                        @RequestParam String nickname,
//                                        @RequestParam String address){
//        pageNum = (pageNum - 1) * pageSize;//页码规则，select语句limit 0,2第1个数据开始前两（2）个数据
//        List<User> data = userMapper.seletPage(pageNum,pageSize,username,nickname,address);
//        Integer total = userMapper.selectTotal(username);
//        Map<String,Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return res;
//    }



    //导入导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception{
        //从数据库拿到所有数据
        List<User> list = userService.list();
        //通过工具类写出到本地
//        ExcelWriter writer = ExcelUtil.getWriter("磁盘路径");
        //在内存操作，写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("avatar","头像");

        //一次性写出list内的对象到Excel中，使用默认样式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.gpenxmlformats-officedocument.spneadsheetm,.sheet; chanset=utf-8");
        String fileName = URLEncoder.encode("用户信息.", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xLsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
        }

    /**
     *excel导入
     *@param file
     * @throws Exception
     */

    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //方式1：通过javabean方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        //List<User> list = reader.readAll(User.class);


        //方式1：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatar(row.get(6).toString());
            users.add(user);
        }

        userService.saveBatch(users);
        return Result.success(true);
    }


    //分页查询 mybatis-plus方式
    //page内传入两个参数 User泛型
    //定义queryWrapper User泛型
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam (defaultValue = "")String username,
                                @RequestParam (defaultValue = "")String nickname,
                                @RequestParam (defaultValue = "")String address
                                ){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(nickname)){
            queryWrapper.like("nickname",nickname);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }

//        //获取当前用户信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("获取当前用户信息=======================================" + currentUser.getNickname());

        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(page,queryWrapper));
    }




}
