package com.glch.base.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glch.base.util.DateUtils;
import com.glch.base.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

	public final Logger logger = LoggerFactory.getLogger(getClass());

	public Map<String, String> getRequestMap(HttpServletRequest request)
			throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			params.put(key, request.getParameter(key));
		}
		
		logger.info("接收报文" + params.toString()) ;
		return params;
	}
	
	public Map<String, Object> getRequestParameterMap(HttpServletRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<String> keys = request.getParameterNames();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			params.put(key, request.getParameter(key));
		}
		
		logger.info("接收报文" + params.toString()) ;
		return params;
	}
	
	
	public static JSONObject getRequestJSONObject(HttpServletRequest request){
		JSONObject json = new JSONObject();
		BufferedReader in;
		try {
			in = request.getReader();
			StringBuffer bf = new StringBuffer();
			String line="";
			while((line=in.readLine())!=null)
				bf.append(line);
			String data = bf.toString(); 
			System.out.println(data);
			if(data!=null && data.length()>0){
				//替换传过来的空字符串（\"与\'）
//				data = data.replaceAll("\\\\\"", "").replaceAll("\\\\\'", "");
				json = JSONObject.parseObject(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static JSONArray getRequestJSONArray(HttpServletRequest request){
		JSONArray json = new JSONArray();
		BufferedReader in;
		try {
			in = request.getReader();
			StringBuffer bf = new StringBuffer();
			String line="";
			while((line=in.readLine())!=null)
				bf.append(line);
			String data = bf.toString();
			System.out.println(data);
			if(data!=null && data.length()>0){
				json = JSONArray.parseArray(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 区分是否是app调用接口
	 * @param jsonParams
	 * @return
	 */
	public static Boolean isApp(JSONObject jsonParams){
		boolean isApp = false;
		
		if(jsonParams.containsKey("isApp")){
			isApp = jsonParams.getBoolean("isApp");
		}
		return isApp;
	}

	public static String getBrowser(HttpServletRequest request) {
		String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
		if (UserAgent != null) {
			if (UserAgent.indexOf("msie") >= 0)
				return "IE";
			if (UserAgent.indexOf("firefox") >= 0)
				return "FF";
			if (UserAgent.indexOf("safari") >= 0)
				return "SF";
		}
		return null;
	}

	public static void setDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception{
		response.setContentType("application/x-download; charset=utf-8");

		if("FF".equals(getBrowser(request))){
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		}else{
			fileName = URLEncoder.encode(fileName, "UTF-8");
		}
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
	}

}
