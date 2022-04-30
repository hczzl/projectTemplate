package com.glch.study.applicationContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzl
 * @Date 2022/4/30
 * @description
 */
@Configuration
public class Config2 {
    @Bean
    public UserServiceTest userServiceTest() {
        return new UserServiceTest();
    }
}
