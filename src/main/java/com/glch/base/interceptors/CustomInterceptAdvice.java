package com.glch.base.interceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.glch.base.common.JsonResponse;

/**
 * 异常拦截
 *
 */
@ControllerAdvice
public class CustomInterceptAdvice  {
	public final Logger logger = LoggerFactory.getLogger(getClass());
	/*
	 * 异常拦截
	 */
	@ExceptionHandler
	@ResponseBody
	public Object handle(Exception e) {
		logger.error(e.getMessage(), e);
		return JsonResponse.error(e);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public Object handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		
        return JsonResponse.urlError();
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Object handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
        return JsonResponse.serverError("数据库中已存在该记录");
	}

}
