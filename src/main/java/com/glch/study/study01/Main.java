package com.glch.study.study01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试bean的生命周期回调方法
 * @author zzl
 * @Date 2022/5/1
 * @description
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        UserService userService = applicationContext.getBean(UserService.class);

        // 容器关闭的时候，才会调bean的销毁方法
        applicationContext.close();
    }
}
