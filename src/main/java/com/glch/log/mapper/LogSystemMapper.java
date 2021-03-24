package com.glch.log.mapper;

import com.glch.log.entity.LogSystem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.plugins.Page;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@Mapper
public interface LogSystemMapper extends BaseMapper<LogSystem> {
    List<Map<String, Object>> queryLogSystemList(Page<Map<String, Object>> pageparam, Map<String, Object>param);
    List<Map<String, Object>> queryAllLogSystemList(Map<String, Object>param);

    int insertLog(LogSystem log);
}
