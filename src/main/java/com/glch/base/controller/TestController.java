package com.glch.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glch.base.entity.BaseModuleEntity;
import com.glch.base.service.IBaseModuleService;
import com.glch.base.util.StringUtil;

@RestController
public class TestController extends BaseController {

	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public IBaseModuleService baseModuleService ;

	@RequestMapping(value = "/test")	
	public Map<String,Object> test(HttpServletRequest request) throws Exception {
		BaseModuleEntity bm = baseModuleService.get() ;	
		return StringUtil.ConvertObjToMap(bm);
	}
	
	@RequestMapping(value = "/testList")	
	public Map<String,Object> testList(HttpServletRequest request) throws Exception {
		List<Map<String,Object>> list = baseModuleService.list() ;	
	
		return StringUtil.ConvertObjToMap(list);
	}
}
