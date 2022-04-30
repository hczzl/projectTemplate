package com.glch.study.applicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ApplicationContext使用汇总
 *
 * @author zzl
 * @Date 2022/4/30
 * @description
 */
public class ApplicationContextTest {
    public static void main(String[] args) {
        // 获取上下文对象
        // AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config2.class);
        // 根据类名获取bean对象
        UserServiceTest userService =  applicationContext.getBean(UserServiceTest.class);
        // 执行bean对象的测试方法
        userService.test();
    }
}
