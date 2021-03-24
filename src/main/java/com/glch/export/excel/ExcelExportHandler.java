package com.glch.export.excel;

import com.glch.base.util.DateUtils;
import com.glch.base.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

/**
 * Excel 导出工具类
 *
 * @author wenchaochao
 * @since 2020-04-07
 */
public class ExcelExportHandler {

    public static final int ROW_ACCESS_WINDOW_SIZE = 100;

    /**
     * 设置总体表格样式
     *
     * @param workbook
     * @return
     */
    private static CellStyle setCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(false);
        return style;
    }

    /**
     * 单元格赋值
     *
     * @param row
     * @param list
     */
    private void setXlsRowData(Row row, List<String> list, CellStyle style) {
        Cell cell;
        for (int i = 0; i < list.size(); i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(list.get(i));
        }
    }

    /**
     * 生成Excel数据列
     *
     * @param dataList
     * @param columnList
     * @param baseSheet
     * @param list
     * @param style
     */
    private void setHWBData(List<Object> dataList, List<String> columnList, Sheet baseSheet, List<String> list, CellStyle style,String dataType) {
        try {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String,Object> data = null;
                if("Map".equals(dataType)){
                    data = (Map<String, Object>) dataList.get(i);
                }else if("Object".equals(dataType)){
                    data = StringUtil.ConvertObjToMap(dataList.get(i));
                }
                Row hssfrow = baseSheet.createRow(i + 1);
                list.clear();
                for (String column : columnList) {
                    list.add(null == data.get(column) ? "" : String.valueOf(data.get(column)));
                }
                setXlsRowData(hssfrow, list, style);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装数据
     *
     * @param workbook
     * @param sheet
     */
    public void pkgData(Workbook workbook, Map<String, Object> sheet,String dataType) {
        List<String> list = new ArrayList<>();
        CellStyle style = setCellStyle(workbook);
        String sheetName = null == sheet.get("sheetName") ? "Sheet1" : String.valueOf(sheet.get("sheetName"));
        Sheet baseSheet = workbook.createSheet(sheetName);
        Row firstRow = baseSheet.createRow(0);//表头信息
        List<String> title = (List<String>) sheet.get("title");
        setXlsRowData(firstRow, title, style);
        List<Object> dataList = (List<Object>) sheet.get("data");
        List<String> columnList = (List<String>) sheet.get("column");
        setHWBData(dataList, columnList, baseSheet, list, style,dataType);
    }

    /**
     * 导出（单个sheet）
     *
     * @param response
     * @param fileName
     */
    public void singleExcelExport(HttpServletResponse response, String fileName, Map<String, Object> sheet,String dataType) {
        Workbook workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            //获取文件名
            if (StringUtil.isEmpty(fileName))
                fileName = System.currentTimeMillis() + ".xlsx";
            else
                fileName += ".xlsx";
            //对文件名进行编码
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            //设置响应头
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            pkgData(workbook, sheet,dataType);
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出（多个sheet）
     *
     * @param response
     * @param fileName
     * @param sheetList
     */
    public void multiExcelExport(HttpServletResponse response, String fileName, List<Map<String, Object>> sheetList,String dataType) {
        Workbook workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
        OutputStream os = null;
        try {
            if (null != sheetList) {
                os = response.getOutputStream();
                //获取文件名
                if (StringUtil.isEmpty(fileName))
                    fileName = System.currentTimeMillis() + ".xlsx";
                else
                    fileName += ".xlsx";
                //对文件名进行编码
                fileName = new String(fileName.getBytes(), "ISO8859-1");
                //设置响应头
                response.setContentType("application/octet-stream;charset=ISO8859-1");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                for (int i = 0; i < sheetList.size(); i++) {
                    pkgData(workbook, sheetList.get(i),dataType);
                }
                workbook.write(os);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
