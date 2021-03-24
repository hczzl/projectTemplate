package com.glch.base.util;

import io.swagger.models.auth.In;

/**
 * 常量工具类
 * 用于对常量的统一管理
 *
 * @author wcc
 */
public class ConstantUtil {
    //PC端
    public static String PC = "PC";
    //App端
    public static String APP = "APP";
    //大于等于1，为成功状态
    public static Integer SUCCESS_STATE = 0;
    //用户量
    public static Integer USER_TYPE = 0;
    //业务量
    public static Integer BUSINESS_TYPE = 1;
    //使用量
    public static Integer USE_TYPE = 2;
    //重点目标量
    public static Integer FOCUS_USER_TYPE = 3;
    //以多少分钟为间隔分割一段范围内的时间
    public static Integer INTERVAL_MINUTE = 120;
    //开始时间
    public static String START_TIME = "00:00:00";
    //结束时间
    public static String END_TIME = "23:59:59";

}
