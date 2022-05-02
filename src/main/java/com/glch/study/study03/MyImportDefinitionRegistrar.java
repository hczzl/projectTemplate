package com.glch.study.study03;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zzl
 * @Date 2022/5/2
 * @description
 */
public class MyImportDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(UserService.class);
        registry.registerBeanDefinition("userService",beanDefinition);
        beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(RoleService.class);
        registry.registerBeanDefinition("roleService",beanDefinition);
    }
}
