package com.glch.base.util;

import javax.servlet.http.HttpServletRequest;

public class PathUtil {
	
	/**
	 * 获取请求路径ip与端口号
	 * @param request
	 * @return
	 */
	public static String getIPAndPort(HttpServletRequest request){
		String path = "";
		try {
			path = getIp(request) + ":" + getPort(request);
		} catch (Exception e) {
			e.printStackTrace();
			return path;
		}
		return path;
	}
	
	/**
	 * 获取请求路径ip
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		String ip = "";
		try {
			ip = request.getServerName();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return ip;
	}
	
	/**
	 * 获取请求路径端口号
	 * @param request
	 * @return
	 */
	public static int getPort(HttpServletRequest request){
		int port = 8080;
		try {
			port = request.getServerPort();
		} catch (Exception e) {
			e.printStackTrace();
			return port;
		}
		return port;
	}
}
