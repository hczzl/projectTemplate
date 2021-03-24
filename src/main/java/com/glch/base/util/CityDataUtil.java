package com.glch.base.util;

import java.util.HashMap;
import java.util.Map;

public class CityDataUtil {
	
	/**
	 * 获取全区地市
	 * @return
	 */
	public static Map<String,Object> getCityNameMap(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("4500", "公安厅");
		resultMap.put("4501", "南宁市");
		resultMap.put("4502", "柳州市");
		resultMap.put("4503", "桂林市");
		resultMap.put("4504", "梧州市");
		resultMap.put("4505", "北海市");
		resultMap.put("4506", "防城港市");
		resultMap.put("4507", "钦州市");
		resultMap.put("4508", "贵港市");
		resultMap.put("4509", "玉林市");
		resultMap.put("4510", "百色市");
		resultMap.put("4511", "贺州市");
		resultMap.put("4512", "河池市");
		resultMap.put("4513", "来宾市");
		resultMap.put("4514", "崇左市");
		return resultMap;
	}
	
	/**
	 * 获取全区地市longNumber
	 * @return
	 */
	public static Map<String,Object> getCityLongNumberMap(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("4500", "450000000000");
		resultMap.put("4501", "450000000000!450100000000");
		resultMap.put("4502", "450000000000!450200000000");
		resultMap.put("4503", "450000000000!450300000000");
		resultMap.put("4504", "450000000000!450400000000");
		resultMap.put("4505", "450000000000!450500000000");
		resultMap.put("4506", "450000000000!450600000000");
		resultMap.put("4507", "450000000000!450700000000");
		resultMap.put("4508", "450000000000!450800000000");
		resultMap.put("4509", "450000000000!450900000000");
		resultMap.put("4510", "450000000000!451000000000");
		resultMap.put("4511", "450000000000!451100000000");
		resultMap.put("4512", "450000000000!451200000000");
		resultMap.put("4513", "450000000000!451300000000");
		resultMap.put("4514", "450000000000!451400000000");
		return resultMap;
	}
	
}
