package com.glch.log.mapper;

import com.glch.log.entity.LogUserOpt;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.plugins.Page;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户操作日志 Mapper 接口
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@Mapper
public interface LogUserOptMapper extends BaseMapper<LogUserOpt> {
    /**
     * 查询用户操作日志列表
     * @param pageparam
     * @param param
     * @return
     */
    List<Map<String, Object>> queryLogUserOptList(Page<Map<String, Object>> pageparam, Map<String, Object>param);
    List<Map<String, Object>> queryAllLogUserOptList(Map<String, Object>param);

    int insertLog(LogUserOpt log);
}
