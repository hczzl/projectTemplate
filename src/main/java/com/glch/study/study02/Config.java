package com.glch.study.study02;

import com.glch.study.study02.bean.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzl
 * @Date 2022/5/1
 * @description
 */
@Configuration
public class Config {
    @Bean
    public UserService userService() {
        return new UserService();
    }
}
