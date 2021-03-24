package com.glch.base.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.glch.base.common.JsonResponse;

import java.util.List;

/**
 * 返回结果处理
 *
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object>  {

	@Value("#{'${myfilter.unFilterUrls}'.split(' - ')}")
	private List<String> unFilterUrls;

	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter arg1,
			MediaType arg2, Class<? extends HttpMessageConverter<?>> arg3,
			ServerHttpRequest arg4, ServerHttpResponse arg5) {
		String url =  arg4.getURI().getPath();
		if(url!=null && url.length()>0){
			for(String s:unFilterUrls){
				if(url.indexOf(s)>=0){
					return body;
				}
			}
		}
		if(body instanceof JsonResponse){
			return body;
		}
		return JsonResponse.ok(body);
	}

	@Override
	public boolean supports(MethodParameter arg0,
			Class<? extends HttpMessageConverter<?>> arg1) {
		return true;
	}
	
	
	
	

}
