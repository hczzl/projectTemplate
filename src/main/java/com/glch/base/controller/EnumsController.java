package com.glch.base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glch.base.enums.BaseEnum;
import com.glch.base.service.IBaseModuleService;

@RestController
@RequestMapping(value = "/base/EnumsController")
public class EnumsController extends BaseController {

	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public IBaseModuleService baseModuleService ;

	@RequestMapping(value = "/listEnums")	
	public List listEnums(HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		String enumName = request.getParameter("enumName");
		Class clazz = Class.forName(enumName);
		if(clazz!=null){
			BaseEnum enum1 = (BaseEnum) clazz.newInstance();
			list = enum1.getEnumList(clazz);
		}
		
		return list;
	}
	
}
