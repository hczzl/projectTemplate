/**
 * Copyright 2012
 *
 * All right reserved
 * 
 * Created on 2012-8-31下午5:43:58
 */
package com.glch.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 
 */
public class MD5Util {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 得到文件的md5值
	 *  @param file 文件
	 *  @return 16进制MD5字符
	 *  @throws IOException 文件异常
	 *  @author xkfeng@iflytek.com
	 *  @created 2014-3-13 下午07:37:47
	 *  @lastModified       
	 *  @history
	 */
	public static String getFileMD5String(File file)
			throws IOException {
		MessageDigest messagedigest = getMd5MessageDigest();
		InputStream fis;
		fis = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 512];
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}
		fis.close();
		return bufferToHex(messagedigest.digest());
	}

	/**
	 *  得到字节数组MD5
	 *  @param s 字符串
	 *  @return 16进制MD5字符
	 *  @author xkfeng@iflytek.com
	 *  @created 2014-3-13 下午07:36:38
	 *  @lastModified       
	 *  @history
	 */
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	/**
	 *  得到字节数组MD5
	 *  @param bytes byte array
	 *  @return 16进制MD5字符
	 *  @author xkfeng@iflytek.com
	 *  @created 2014-3-13 下午07:36:38
	 *  @lastModified       
	 *  @history
	 */
	public static String getMD5String(byte[] bytes) {
		MessageDigest messagedigest = getMd5MessageDigest();
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	/**
	 *  校验md5密码
	 *  @param password 密码原文
	 *  @param md5PwdStr 密码md5
	 *  @return 是否匹配
	 *  @author xkfeng@iflytek.com
	 *  @created 2014-3-13 下午07:36:03
	 *  @lastModified       
	 *  @history
	 */
	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}
	
	/**
	 * 
	 *  字节数组转16进制
	 *  @param bytes byte array
	 *  @return 16进制字符
	 *  @author xkfeng@iflytek.com
	 *  @created 2014-3-13 下午07:35:04
	 *  @lastModified       
	 *  @history
	 */
	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	/**
	 *  字节数组指定位置字节转16进制
	 *  @param bytes 字节数组
	 *  @param m start
	 *  @param n length
	 *  @return 16进制字符
	 *  @author xkfeng@iflytek.com
	 *  @created 2014-3-13 下午07:32:32
	 *  @lastModified       
	 *  @history
	 */
	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	/**
	 *  byte to Hex str
	 *  @param bt  byte
	 *  @param stringbuffer stringbuffer
	 *  @author xkfeng@iflytek.com
	 *  @created 2014-3-13 下午07:33:38
	 *  @lastModified       
	 *  @history
	 */
	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	/**
	 * 获取md5 Message Digest
	 *  @return md5messagedigest
	 *  @author xkfeng@iflytek.com
	 *  @created 2014-3-13 下午07:31:16
	 *  @lastModified       
	 *  @history
	 */
	private static MessageDigest getMd5MessageDigest() {
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(MD5Util.class.getName()
					+ "初始化失败，MessageDigest不支持MD5Util。");
			nsaex.printStackTrace();
		}
		return messagedigest;
	}
	
	public static void main(String[] args){
		String s = MD5Util.getMD5String("342522199509132118");
		System.out.println(s);
	}

}
