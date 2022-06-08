package com.yuangege.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


//！！！配置 解决跨域问题
@Configuration
public class CorsConfig {
    //当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24* 60 * 60;
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1设置访问源地址
        corsConfiguration.addAllowedHeader("*"); // 2设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); // 3设置访问源请求方法
        source.registerCorsConfiguration( "/**",corsConfiguration);//4对接口配置跨域设置
        corsConfiguration.setMaxAge(MAX_AGE);
        return new CorsFilter(source);
    }
}
