package com.glch.log.service;

import com.glch.log.entity.LogSystem;
import com.glch.log.entity.LogUserOpt;
import com.glch.log.entity.vo.LogSystemVo;
import com.baomidou.mybatisplus.service.IService;
import com.glch.log.enums.LogLevelEnum;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
public interface ILogSystemService extends IService<LogSystem> {
    void save(LogSystem log);

    /**
     * 成功
     * @param request HttpServletRequest
     * @param description 日志描述
     * @param level 日志等级枚举
     * @param errorInfo 错误信息
     * @param params 请求参数
     * @param isApp 是否app
     */
    void success(HttpServletRequest request, String description, LogLevelEnum level, String errorInfo,
                 String params, Boolean isApp);

    /**
     * 失败
     * @param request HttpServletRequest
     * @param description 日志描述
     * @param level 日志等级枚举
     * @param errorInfo 错误信息
     * @param params 请求参数
     * @param isApp 是否app
     */
    void failed(HttpServletRequest request,String description,LogLevelEnum level,String errorInfo,
                String params,Boolean isApp);

    /***
     * 分页查询系统日志列表
     * @param logSystemVo
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    Map<String, Object> getLogSystemList(LogSystemVo logSystemVo, String startTime, String endTime) throws Exception;

    /***
     * 查询所有系统日志列表
     * @param logSystem
     * @param startTime
     * @param endTime
     * @return
     */
     List<Map<String, Object>> getAllLogSystemList(LogSystem logSystem, String startTime, String endTime);

    /**
     * 封装日志
     * @param request
     * @param description
     * @param src
     * @param level
     * @param errorInfo
     * @param params
     * @param isOk
     * @return
     */
    LogSystem getLog(HttpServletRequest request,String description,String src,LogLevelEnum level,String errorInfo,
                     String params, Boolean isOk);

    int insertLog(LogSystem log);
}
