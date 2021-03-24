package com.glch.base.util;

import static org.apache.commons.beanutils.PropertyUtils.copyProperties;

/**
 * Bean工具类
 *
 * @author zhongzhilong
 * @date 2020/4/6
 */
public class BeanUtils {
    /**
     * Bean属性复制工具方法
     *
     * @param dest 目标对象
     * @param src  源对象
     */
    public static void copyBeanProp(Object dest, Object src) {
        try {
            copyProperties(src, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
