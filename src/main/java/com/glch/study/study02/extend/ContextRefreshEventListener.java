package com.glch.study.study02.extend;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author zzl
 * @Date 2022/5/2
 * @description
 */
@Component
public class ContextRefreshEventListener {
    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent context) {
        System.out.println("所有的bean创建完成，监听ContextRefreshedEvent事件");
    }
}
