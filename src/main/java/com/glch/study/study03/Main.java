package com.glch.study.study03;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试@Import的使用
 *
 * @author zzl
 * @Date 2022/5/1
 * @description
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        UserService userService = applicationContext.getBean(UserService.class);
        RoleService roleService = applicationContext.getBean(RoleService.class);

        System.out.println(userService);
        System.out.println(roleService);
    }
}
