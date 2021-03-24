package com.glch.log.service.impl;

import com.glch.log.enums.LogLevelEnum;
import com.glch.log.enums.OptTypeEnum;
import com.glch.log.service.ILogService;
import com.glch.log.service.ILogSystemService;
import com.glch.log.service.ILogUserOptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志类
 * @author wcc
 * @date 2019-11-28
 */
@Service
public class LogService implements ILogService {
	@Autowired
	private ILogSystemService systemLogService;
	@Autowired
	private ILogUserOptService userOptLogService;
	
	/**
	 * 成功
	 */
	@Override
	public void success(HttpServletRequest request, OptTypeEnum optType, String description, LogLevelEnum levelEnum, String errorInfo,
						String params, Boolean isApp) {
		//用户操作日志
		userOptLogService.success(request, optType, description, params, isApp);
		//系统操作日志
		systemLogService.success(request, description, levelEnum, errorInfo, params, isApp);
	}

	/**
	 * 失败
	 */
	@Override
	public void failed(HttpServletRequest request, OptTypeEnum optType,String description, LogLevelEnum levelEnum, String errorInfo,
			String params, Boolean isApp) {
		//用户操作日志
		userOptLogService.failed(request, optType, description, params, isApp);
		//系统操作日志
		systemLogService.failed(request, description, levelEnum, errorInfo, params, isApp);
	}

	/**
	 * 失败
	 */
	@Override
	public void failed(HttpServletRequest request, Exception e, OptTypeEnum optType, String description, LogLevelEnum levelEnum,
					   String params, Boolean isApp) {
		//用户操作日志
		userOptLogService.failed(request, optType, description, params, isApp);
		//系统操作日志
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		StringBuffer sb = sw.getBuffer();
		systemLogService.failed(request, description, levelEnum, sb.toString(), params, isApp);
	}
	
}
