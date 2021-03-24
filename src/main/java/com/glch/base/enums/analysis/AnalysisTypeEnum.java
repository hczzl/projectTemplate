package com.glch.base.enums.analysis;

/**
 * 战法分析类型
 *
 * @author zhongzhilong
 * @date 2020/4/7
 */
public enum AnalysisTypeEnum {

    UNUSUAL_FREQUENT_USE(1, "异常频繁使用"),
    FREQUENT_CONTACT(2, "频繁联系"),
    AREA_DATA_COLLISION(3, "区域数据碰撞"),
    SINGLE_CONTACT(4, "单线联系"),
    CROSS_BORDER_CONTACT(5, "跨境联系"),
    ENTRY_EXIT_ANALYSIS(6, "出入境分析"),
    STATEMENT_ANALYSIS(7, "话单分析"),
    SINGLE_ANALYSIS(8, "单目标分析");

    private int id;
    private String desc;

    AnalysisTypeEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
