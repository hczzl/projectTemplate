package com.glch.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.glch.base.entity.BaseModuleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface BaseModuleMapper extends BaseMapper<BaseModuleEntity> {
    public List<Map<String, Object>> list(Page<Map<String, Object>> page, Map<String, Object> params);
}
