package com.glch.study.study02.extend;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * 实现SmartInitializingSingleton接口，扩展业务逻辑
 *
 * @author zzl
 * @Date 2022/5/2
 * @description
 */
//@Component
public class MySmartInitializingSingleton implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("所有的bean创建完成，扩展SmartInitializingSingleton");
    }
}
