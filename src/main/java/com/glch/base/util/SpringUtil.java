package com.glch.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware{
	private static ApplicationContext applicationContext = null;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		if(SpringUtil.applicationContext==null){
			SpringUtil.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz){
		return applicationContext.getBean(clazz);
	}
}
