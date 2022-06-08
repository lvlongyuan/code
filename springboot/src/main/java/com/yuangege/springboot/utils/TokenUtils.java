package com.yuangege.springboot.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yuangege.springboot.entity.User;
import com.yuangege.springboot.service.IUsersService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtils {

    private static IUsersService staticUserService;

    @Resource
    private IUsersService usersService;

    @PostConstruct
    public void setStaticUserService(){
        staticUserService = usersService;
    }
    /**
     * 生成Token
     * @return
     *
     * 根据userid查询用户数据
     */
    public static String getToken(String userId,String sign){
        return JWT.create().withAudience(userId) //将user id保存到token里面，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小时后token过期
                .sign(Algorithm.HMAC256(sign)); //以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     * @return user对象
     */
    public static User getCurrentUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
