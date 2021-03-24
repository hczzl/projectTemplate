package com.glch.aop;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.glch.base.caches.Context;
import com.glch.base.caches.ServContexts;
import com.glch.base.entity.BaseEntity;
import com.glch.base.entity.CoreEntity;
import com.glch.base.util.DateUtils;
import com.glch.base.util.StringUtil;


@Aspect
@Component
public class DataBaseAspect {
	
	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	// insert开头的方法
//	@Pointcut("execution(**..insert*(..))")
    @Pointcut("execution(* **..insert(..))")
	public void insert() {}

    @Pointcut("execution(* **..updateById(..))")
	public void updateById() {}

	// 数据插入前操作
	@Before(value = "insert()")	
	public void insertBefore(JoinPoint joinPoint) throws Exception {
		Object[] obj = joinPoint.getArgs() ;
		
		for(Object argItem : obj) {	
			if(CoreEntity.class.isAssignableFrom(argItem.getClass())) {
				Method setId = argItem.getClass().getMethod("setId", String.class) ;
				setId.invoke(argItem, StringUtil.uuid()) ;
			}
			if(BaseEntity.class.isAssignableFrom(argItem.getClass())) {
				Context context = ServContexts.getCurContext() ;
				
				Method setCreator = argItem.getClass().getMethod("setCreator", String.class) ;
				setCreator.invoke(argItem ,context.getCurUser().getId()) ;
				
				Method setCreateTime = argItem.getClass().getMethod("setCreateTime", Date.class) ;
				setCreateTime.invoke(argItem ,DateUtils.now()) ;

			}
		}	
	}
	
	// 数据插入前操作
	@Before(value = "updateById()")	
	public void updateBefore(JoinPoint joinPoint) throws Exception {
		Object[] obj = joinPoint.getArgs() ;
		
		for(Object argItem : obj) {	
			if(BaseEntity.class.isAssignableFrom(argItem.getClass())) {
				Context context = ServContexts.getCurContext() ;
				Method setLastUpdateUser = argItem.getClass().getMethod("setLastUpdateUser", String.class) ;
				setLastUpdateUser.invoke(argItem, context.getCurUser().getId()) ;
				
				Method setLastUpdatedTime = argItem.getClass().getMethod("setLastUpdateTime", Date.class) ;
				setLastUpdatedTime.invoke(argItem ,DateUtils.now()) ;
			}
		}	
	}
}
