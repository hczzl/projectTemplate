package com.glch.base.service;

import java.util.List;
import java.util.Map;

import com.glch.base.entity.BaseModuleEntity;

public interface IBaseModuleService {	
	public BaseModuleEntity get() ;	
	
	public List<Map<String,Object>> list() ;	
}
