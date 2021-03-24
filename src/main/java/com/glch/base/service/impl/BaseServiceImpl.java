package com.glch.base.service.impl;

import com.glch.base.mapper.SequencesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glch.base.service.IBaseService;

@Service
public class BaseServiceImpl implements IBaseService {
	
	@Autowired
	public SequencesMapper sequencesMapper ;

	@Override
	public String querySeq(String seqName) {
		return sequencesMapper.querySeq(seqName) ;
	}

			
}	
