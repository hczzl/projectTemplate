package com.glch.study;

import com.glch.system.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author zzl
 * @Date 2022/4/23
 * @description
 */
@Component
public class FactoryBeanTest implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
