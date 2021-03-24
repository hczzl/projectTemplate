package com.glch.export.excel;

import lombok.Data;

@Data
public class ExcelHeaderInfo {
    private int firstRow;

    private int lastRow;

    private int firstCol;

    private int lastCol;

    private String title;

    ExcelHeaderInfo(int firstRow, int lastRow, int firstCol, int lastCol, String title){
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        this.title = title;
    }
}
