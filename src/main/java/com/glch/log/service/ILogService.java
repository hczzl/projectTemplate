package com.glch.log.service;

import com.glch.log.enums.LogLevelEnum;
import com.glch.log.enums.OptTypeEnum;

import javax.servlet.http.HttpServletRequest;

public interface ILogService {
	/**
	 * 成功
	 * @param request HttpServletRequest
	 * @param optType 操作类型
	 * @param description 日志描述
	 * @param levelEnum 日志等级枚举
	 * @param errorInfo 错误信息
	 * @param params 请求参数
	 * @param isApp 是否app
	 */
	void success(HttpServletRequest request, OptTypeEnum optType, String description, LogLevelEnum levelEnum, String errorInfo,
				 String params, Boolean isApp);
	
	/**
	 * 失败
	 * @param request HttpServletRequest
	 * @param optType 操作类型
	 * @param description 日志描述
	 * @param levelEnum 日志等级枚举
	 * @param errorInfo 错误信息
	 * @param params 请求参数
	 * @param isApp 是否app
	 */
	void failed(HttpServletRequest request, OptTypeEnum optType, String description, LogLevelEnum levelEnum, String errorInfo,
				String params, Boolean isApp);

	/**
	 *
	 * @param request ttpServletRequest
	 * @param e 错误信息
	 * @param optType 操作类型
	 * @param description 日志描述
	 * @param levelEnum 日志等级枚举
	 * @param params 请求参数
	 * @param isApp 是否app
	 */
    void failed(HttpServletRequest request, Exception e, OptTypeEnum optType, String description, LogLevelEnum levelEnum,
                String params, Boolean isApp);
}
