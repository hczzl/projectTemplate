package com.glch.study.study01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzl
 * @Date 2022/5/1
 * @description
 */
@Configuration
public class Config {
    @Bean(initMethod = "init2", destroyMethod = "destroy2")
    public UserService userService() {
        return new UserService();
    }
}
