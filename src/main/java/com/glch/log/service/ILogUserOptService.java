package com.glch.log.service;

import com.glch.log.entity.LogUserOpt;
import com.baomidou.mybatisplus.service.IService;
import com.glch.log.enums.OptTypeEnum;
import com.glch.log.entity.vo.LogUserOptVo;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 用户操作日志 服务类
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
public interface ILogUserOptService extends IService<LogUserOpt> {
    void save(LogUserOpt log);

    /**
     * 成功
     * @param request HttpServletRequest
     * @param optType 操作类型
     * @param description 日志描述
     * @param params 请求参数
     * @param isApp 是否app
     */
    void success(HttpServletRequest request, OptTypeEnum optType, String description, String params, Boolean isApp);

    /**
     * 失败
     * @param request HttpServletRequest
     * @param optType 操作类型
     * @param description 日志描述
     * @param params 请求参数
     * @param isApp 是否app
     */
    void failed(HttpServletRequest request,OptTypeEnum optType,String description,String params,Boolean isApp);

    /***
     * 分页查询用户操作日志列表
     * @param logUserOptVo
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    Map<String, Object> getLogUserOptList(LogUserOptVo logUserOptVo, String startTime, String endTime) throws Exception;

    /***
     * 查询所有用户操作日志列表
     * @param logUserOpt
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> getAllLogUserOptList(LogUserOpt logUserOpt, String startTime, String endTime);

    /**
     * 封装日志信息
     * @param request
     * @param optType
     * @param description
     * @param src
     * @param params
     * @param isOk
     * @return
     */
    LogUserOpt getLog(HttpServletRequest request,OptTypeEnum optType,String description,String src,
                      String params, Boolean isOk);

    int insertLog(LogUserOpt log);
}
