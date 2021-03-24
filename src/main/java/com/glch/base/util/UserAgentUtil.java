package com.glch.base.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

/**
 * 获取客户端信息
 */
public class UserAgentUtil {
	/**
	 * 获取请求ip
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		String ip = null;
		ip = request.getHeader("x-forwarded-for");
		if(StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
			if("127.0.0.1".equals(ip)){
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		if(!StringUtil.isEmpty(ip) && ip.length() > 15){
			if(ip.indexOf(",") > 0){
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}
	
	/**
	 * 获取浏览器名称
	 * @param request
	 * @return
	 */
	public static String getBrowserName(HttpServletRequest request){
		String header = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		Browser browser = userAgent.getBrowser();
		return browser.getName();
	}
	
	/**
	 * 获取浏览器版本
	 * @param request
	 * @return
	 */
	public static String getBrowserVersion(HttpServletRequest request){
		String header = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		Browser browser = userAgent.getBrowser();
		Version version = browser.getVersion(header);
		return null==version?"":version.getVersion();
	}
	
	/**
	 * 获取操作系统名称
	 * @param request
	 * @return
	 */
	public static String getOsName(HttpServletRequest request){
		String header = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		OperatingSystem opSystem = userAgent.getOperatingSystem();
		return opSystem.getName();
	}

	/**
	 * 获取服务器ip
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getServerIp() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress().toString();
	}

	/**
	 * 获取服务器主机名
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostName() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostName();
	}
}
