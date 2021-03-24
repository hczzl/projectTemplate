package com.glch.base.util;

import com.baomidou.mybatisplus.annotations.TableName;

public class ReflectUtils {
  
    /**
     * 通过获取类上的@TableName注解获取表名称
     *
     * @param clazz
     * @return string 
     */
    public static String getTableName(Class<?> clazz) {
    	TableName annotation = clazz.getAnnotation(TableName.class);
        return annotation==null?null:annotation.value();
    }

}
