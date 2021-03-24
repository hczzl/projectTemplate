package com.glch.log.service.impl;

import com.glch.base.caches.Context;
import com.glch.base.caches.ServContexts;
import com.glch.base.util.*;
import com.glch.log.entity.LogUserOpt;
import com.glch.log.entity.vo.LogUserOptVo;
import com.glch.log.enums.OptTypeEnum;
import com.glch.log.mapper.LogUserOptMapper;
import com.glch.log.service.ILogUserOptService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 用户操作日志 服务实现类
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@Service
public class LogUserOptServiceImpl extends ServiceImpl<LogUserOptMapper, LogUserOpt> implements ILogUserOptService {

    @Autowired
    private LogUserOptMapper logUserOptMapper;

    @Override
    public void save(LogUserOpt log) {
        if(null == log){
            return;
        }
        logUserOptMapper.insert(log);
    }

    @Override
    public void success(HttpServletRequest request, OptTypeEnum optType, String description, String params, Boolean isApp) {
        final LogUserOpt log = getLog(request,optType,description,isApp?ConstantUtil.APP:ConstantUtil.PC,
                params,true);
        new Thread(new Runnable() {
            public void run() {
                try {
                    logUserOptMapper.insert(log);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }

    @Override
    public void failed(HttpServletRequest request, OptTypeEnum optType, String description, String params, Boolean isApp) {
        final LogUserOpt log = getLog(request,optType,description,isApp?ConstantUtil.APP: ConstantUtil.PC,
                params,false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    logUserOptMapper.insert(log);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }
    @Override
    public LogUserOpt getLog(HttpServletRequest request,OptTypeEnum optType,String description,String src,
                             String params, Boolean isOk){
        LogUserOpt log = new LogUserOpt();
        log.setIp(UserAgentUtil.getIp(request));//请求ip
//        log.setDeviceId(context.getCurUser().getDeviceId());//授权设备id
        if(null != optType){//操作类型
            log.setOptType(optType.getValue());
        }else{
            log.setOptType(OptTypeEnum.OTHER.getValue());
        }
        log.setDescription(description);//日志描述
        log.setAccessUrl(request.getRequestURI());//请求路径
        log.setParams(params);//请求参数
        log.setSrc(src);//日志来源（app/pc）
        log.setSucc(isOk);//返回是否成功
        log.setBrowserName(UserAgentUtil.getBrowserName(request));//浏览器名称
        log.setBrowserVersion(UserAgentUtil.getBrowserVersion(request));//浏览器版本号
        log.setOsName(UserAgentUtil.getOsName(request));//操作系统名称
        return log;
    }

    @Override
    public int insertLog(LogUserOpt log) {
        return this.baseMapper.insertLog(log);
    }

    @Override
    public Map<String, Object> getLogUserOptList(LogUserOptVo logUserOptVo, String startTime, String endTime) throws Exception{
        Page<Map<String, Object>> pageparam = new Page<Map<String, Object>>();
        pageparam.setCurrent(logUserOptVo.getLimit());
        pageparam.setSize(logUserOptVo.getPageSize());
        LogUserOpt logUserOpt=new LogUserOpt();
        logUserOpt=logUserOpt.copy(logUserOptVo);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        param.put("creator",logUserOpt.getCreator());
        param.put("ip",logUserOpt.getIp());
        param.put("optType",logUserOpt.getOptType());
        param.put("description",logUserOpt.getDescription());
        List<Map<String, Object>> list = this.baseMapper.queryLogUserOptList(pageparam,param) ;
        pageparam.setRecords(list);
        return StringUtil.ConvertObjToMap(new PageUtils(pageparam));
    }

    @Override
    public List<Map<String, Object>> getAllLogUserOptList(LogUserOpt logUserOpt, String startTime, String endTime){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        param.put("creator",logUserOpt.getCreator());
        param.put("ip",logUserOpt.getIp());
        param.put("optType",logUserOpt.getOptType());
        param.put("description",logUserOpt.getDescription());
        return  this.baseMapper.queryAllLogUserOptList(param) ;
    }
}
