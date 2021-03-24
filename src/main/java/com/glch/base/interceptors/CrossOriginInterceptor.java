package com.glch.base.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/*
 * 跨域访问
 */
@Component
public class CrossOriginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT,DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type,Access-Control-Allow-Headers,Authorization,X-Requested-Width");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse response, Object respons2, Exception arg3)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT,DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type,Access-Control-Allow-Headers,Authorization,X-Requested-Width");
		response.setHeader("Access-Control-Allow-Credentials", "true");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT,DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type,Access-Control-Allow-Headers,Authorization,X-Requested-Width");
		response.setHeader("Access-Control-Allow-Credentials", "true");
	}

	
}
