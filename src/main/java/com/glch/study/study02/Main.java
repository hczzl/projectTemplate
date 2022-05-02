package com.glch.study.study02;

import com.glch.study.study02.bean.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试bean的后置处理器
 * @author zzl
 * @Date 2022/5/1
 * @description
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        UserService userService = applicationContext.getBean(UserService.class);
    }
}
