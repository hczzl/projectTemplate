package com.glch.log.service.impl;

import com.glch.base.caches.Context;
import com.glch.base.caches.ServContexts;
import com.glch.base.util.*;
import com.glch.log.entity.LogSystem;
import com.glch.log.entity.LogUserOpt;
import com.glch.log.entity.vo.LogSystemVo;
import com.glch.log.enums.LogLevelEnum;
import com.glch.log.mapper.LogSystemMapper;
import com.glch.log.service.ILogSystemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@Service
public class LogSystemServiceImpl extends ServiceImpl<LogSystemMapper, LogSystem> implements ILogSystemService {

    @Autowired
    private LogSystemMapper logSystemMapper;

    @Override
    public void save(LogSystem log) {
        if(null == log){
            return;
        }
        logSystemMapper.insert(log);
    }

    @Override
    public void success(HttpServletRequest request, String description, LogLevelEnum level, String errorInfo, String params, Boolean isApp) {
        final LogSystem systemLog = getLog(request,description,isApp? ConstantUtil.APP:ConstantUtil.PC,level,errorInfo,
                params,true);
        new Thread(new Runnable() {
            public void run() {
                try {
                    logSystemMapper.insert(systemLog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }

    @Override
    public void failed(HttpServletRequest request, String description, LogLevelEnum level, String errorInfo, String params, Boolean isApp) {
        final LogSystem log = getLog(request,description,isApp?ConstantUtil.APP:ConstantUtil.PC,level,errorInfo,
                params,false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    logSystemMapper.insert(log);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }

    @Override
    public LogSystem getLog(HttpServletRequest request,String description,String src,LogLevelEnum level,String errorInfo,
                            String params, Boolean isOk){
        LogSystem log = new LogSystem();
        log.setIp(UserAgentUtil.getIp(request));//请求ip
        log.setDescription(description);//日志描述
        log.setAccessUrl(request.getRequestURI());//请求路径
        log.setParams(params);//请求参数
        log.setSrc(src);//日志来源（app/pc）
        log.setSucc(isOk);//返回是否成功
        log.setLogLevel(level.getValue());//日志级别
        log.setErrorInfo(errorInfo);//错误信息
        return log;
    }

    @Override
    public int insertLog(LogSystem log) {
        return this.baseMapper.insertLog(log);
    }

    @Override
    public Map<String, Object> getLogSystemList(LogSystemVo logSystemVo, String startTime, String endTime) throws Exception{
        Page<Map<String, Object>> pageparam = new Page<Map<String, Object>>();
        pageparam.setCurrent(logSystemVo.getLimit());
        pageparam.setSize(logSystemVo.getPageSize());
        LogSystem logSystem=new LogSystem();
        logSystem=logSystem.copy(logSystemVo);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        param.put("creator",logSystem.getCreator());
        param.put("ip",logSystem.getIp());
        param.put("description",logSystem.getDescription());
        List<Map<String, Object>> list = this.baseMapper.queryLogSystemList(pageparam,param) ;
        pageparam.setRecords(list);
        return StringUtil.ConvertObjToMap(new PageUtils(pageparam));
    }

    @Override
    public List<Map<String, Object>> getAllLogSystemList(LogSystem logSystem, String startTime, String endTime){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        param.put("creator",logSystem.getCreator());
        param.put("ip",logSystem.getIp());
        param.put("description",logSystem.getDescription());
        return  this.baseMapper.queryAllLogSystemList(param) ;
    }
}
