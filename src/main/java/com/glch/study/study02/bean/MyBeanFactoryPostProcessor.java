package com.glch.study.study02.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Bean的后置处理器
 *
 * @author zzl
 * @Date 2022/5/2
 * @description
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinition : beanDefinitionNames) {
            System.out.println(beanDefinition);
        }
        BeanDefinition userService = beanFactory.getBeanDefinition("userService");
        // UserService默认为单例的，在后置处理器中可以将其扩展为多例的，再此作为一个例子
        userService.setScope("prototype");
    }
}
