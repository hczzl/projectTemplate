package com.glch.study.study01;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zzl
 * @Date 2022/5/1
 * @description
 */
public class UserService implements InitializingBean, DisposableBean {
    @PostConstruct
    public void init1() {
        System.out.println("PostConstruct初始化");
    }

    @PreDestroy
    public void destroy1() {
        System.out.println("PreDestroy销毁");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean的初始化方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean的销毁方法");
    }

    public void init2() {
        System.out.println("inti-method初始化");
    }

    public void destroy2() {
        System.out.println("destroy-method销毁");
    }
}
