package com.glch.system.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRoleMapper{

    List<Map<String,Object>> queryByUserId(String id);
}
