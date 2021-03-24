package com.glch.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glch.base.mapper.BaseModuleMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glch.base.entity.BaseModuleEntity;
import com.glch.base.service.IBaseModuleService;

@Service
public class BaseModuleServiceImpl extends ServiceImpl<BaseModuleMapper, BaseModuleEntity> implements IBaseModuleService {

	public BaseModuleEntity get() {	
		return this.baseMapper.selectById("5510cff1de11430683d7c2e9453ed330") ;
	}

	public List<Map<String,Object>> list() {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(Integer.valueOf(1 + ""),Integer.valueOf(10 + ""));
		
		List<Map<String, Object>> list = this.baseMapper.list(page ,new HashMap<String, Object>()) ;
		
		return list ;	
	}		
	
}	
