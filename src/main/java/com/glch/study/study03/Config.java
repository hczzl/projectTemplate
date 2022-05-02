package com.glch.study.study03;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zzl
 * @Date 2022/5/1
 * @description
 */
@Configuration
@ComponentScan
//@Import(UserService.class)
//@Import(MyImportSelector.class)
@Import(MyImportDefinitionRegistrar.class)
public class Config {
    // @Bean
    public UserService userService() {
        return new UserService();
    }
}
