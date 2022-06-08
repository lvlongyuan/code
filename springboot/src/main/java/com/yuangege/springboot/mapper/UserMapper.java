package com.yuangege.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuangege.springboot.entity.User;

//@Mapper//解释为一个接口，因为在MybatisPlusConfig配置类中进行了Mapper的注入操作，就解释了这是一个接口（mapper包内都是接口类）
public interface UserMapper extends BaseMapper<User> {

//    @Select("SELECT * from sys_users")
//    List<User> findAll();//列表数组类型的findAll（）方法，找到josn类型的一条数据储存在数组中
//
//    @Insert("INSERT into sys_users(username,password,nickname,email,phone,address)" +
//            "VALUES(#{username},#{password},#{nickname},#{email},#{phone},#{address})")
//    int insert(User user);//添加数据
//
//    int update(User user);//更新数据
//
//    @Delete("DELETE from sys_users where id = #{id}")
//    Integer deleteById(@Param("id") Integer id);
//
//    @Select("select  * from sys_users " +
//            "where username like concat('%',#{username}&#{nickname}&#{address}},'%')limit #{pageNum},#{pageSize}")
//    List<User> seletPage(Integer pageNum, Integer pageSize,String username,String nickname, String address);
//
//    @Select("select count(*)from sys_users " +
//            "where username like concat('%',#{username},'%')")//搜寻所有的并计算数量
//    Integer selectTotal(String username);

}
