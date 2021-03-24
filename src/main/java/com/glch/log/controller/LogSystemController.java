package com.glch.log.controller;


import com.alibaba.fastjson.JSONObject;
import com.glch.base.common.BizException;
import com.glch.base.common.JsonResponse;
import com.glch.base.controller.BaseController;
import com.glch.base.util.StringUtil;
import com.glch.export.excel.ExcelExportHandler;
import com.glch.log.entity.LogSystem;
import com.glch.log.enums.LogLevelEnum;
import com.glch.log.enums.OptTypeEnum;
import com.glch.log.service.ILogService;
import com.glch.log.service.ILogSystemService;
import com.glch.log.entity.vo.LogSystemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.*;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author sll
 * @since 2020-04-05
 */
@RestController
@RequestMapping("/log/logSystem")
@Api(value = "logSystemController", tags = "系统日志相关接口")
public class LogSystemController extends BaseController{
    @Autowired
    private ILogSystemService logSystemService;
    @Autowired
    private ILogService logService;
    /***
     * 查询系统日志列表
     * @param request
     * @param logSystemVo
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLogSystemList", method = {RequestMethod.POST})
    @ApiOperation("系统日志分页查询")
    public JsonResponse getLogSystemList(HttpServletRequest request, @RequestBody LogSystemVo logSystemVo, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) throws Exception {
        Map<String, Object> retMap = new HashMap<String, Object>();
        JSONObject jsonParams = super.getRequestJSONObject(request);
        try {
            Map<String, Object> params = JSONObject.parseObject(jsonParams.toJSONString(), Map.class);
            if (StringUtil.isEmpty(logSystemVo.getPageSize())
                    || StringUtil.isEmpty(logSystemVo.getLimit())) {
                throw new BizException("分页参数有误");
            }
            retMap = logSystemService.getLogSystemList(logSystemVo,startTime,endTime);
            logService.success(request, OptTypeEnum.QUERY, "查询系统日志列表", LogLevelEnum.INFO, null,null,false);
            return JsonResponse.ok("查询成功!",retMap);
        } catch (Exception e) {
            e.printStackTrace();
            logService.failed(request, OptTypeEnum.QUERY, "查询系统日志列表", LogLevelEnum.ERROR,e.getMessage(),null, false);
            return JsonResponse.error("未知异常!");
        }
    }

    /***
     * 导出系统日志
     * @param response
     * @param logSystem
     * @param startTime
     * @param endTime
     * @param filename
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportLogSystem", method = {RequestMethod.POST})
    @ApiOperation("导出系统日志")
    public JsonResponse exportLogSystem(HttpServletResponse response, @RequestBody(required = false) LogSystem logSystem, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, @RequestParam String filename) throws Exception {
        try {
            if (StringUtil.isEmpty(filename))
                throw new BizException("参数有误");
            Map<String, Object> params=new HashMap<String, Object>();
            List<Map<String,Object>> list=logSystemService.getAllLogSystemList(logSystem,startTime,endTime);
            //工作薄表头
            List<String> title = Arrays.asList("IP","日志等级","日志描述","日志来源","创建人","创建时间","最后修改人","最后修改时间");
            //表头对应的数据列
            List<String> column = Arrays.asList("ip","logLevel","description","src","creator","createTime","lastUpdateUser","lastUpdateTime");
            //初始化工作薄列表
            List<Map<String,Object>> sheetList = new ArrayList<>();
            // 工作簿数据1
            Map<String,Object> sheet1 = new HashMap<>();
            sheet1.put("sheetName","用户操作日志表");
            sheet1.put("title",title);
            sheet1.put("data",list);
            sheet1.put("column",column);
            sheetList.add(sheet1);
            ExcelExportHandler handler = new ExcelExportHandler();
            handler.multiExcelExport(response,filename,sheetList,"Map");
            return JsonResponse.ok("导出成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.error("未知异常!");
        }
    }


}

