package com.glch.export.excel;


import com.glch.system.role.entity.Role;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 导出示例（暂不支持多级表头）
 */
public class Demo {
    public void testExport(HttpServletResponse response){
        //导出数据
        List<Map<String,Object>> list = new ArrayList<>();
        //工作薄表头
        List<String> title = Arrays.asList("表头1","表头2","表头3","表头4","表头5","表头6","表头7","表头8");
        //表头对应的数据列
        List<String> column = Arrays.asList("1","2","3","4","5","6","7","8");
        //初始化工作薄列表
        List<Map<String,Object>> sheetList = new ArrayList<>();
        // 工作簿数据1
        Map<String,Object> sheet1 = new HashMap<>();
        sheet1.put("sheetName","工作簿1");
        sheet1.put("title",title);
        sheet1.put("data",list);
        sheet1.put("column",column);
        sheetList.add(sheet1);
        // 工作簿数据2
        Map<String,Object> sheet2 = new HashMap<>();
        sheet2.put("sheetName","工作簿2");
        sheet2.put("title",title);
        sheet2.put("data",list);
        sheet2.put("column",column);
        sheetList.add(sheet2);
        // 工作簿数据3
        Map<String,Object> sheet3 = new HashMap<>();
        sheet3.put("sheetName","工作簿3");
        sheet3.put("title",title);
        sheet3.put("data",list);
        sheet3.put("column",column);
        sheetList.add(sheet3);

        ExcelExportHandler handler = new ExcelExportHandler();
        //多个工作簿
        handler.multiExcelExport(response,"multiTest",sheetList,"Map");
        //单个工作簿
//        handler.singleExcelExport(response,"singleTest",sheet1);
    }
}
