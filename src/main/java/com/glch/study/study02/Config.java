package com.glch.study.study02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zzl
 * @Date 2022/5/1
 * @description
 */
//@Configuration
//@ComponentScan
//@Import(UserImport.class)
public class Config {
    @Bean
    public UserService userService() {
        return new UserService();
    }
}
