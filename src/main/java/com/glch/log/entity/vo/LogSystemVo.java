package com.glch.log.entity.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.glch.base.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogSystemVo extends PageEntity {
    @ApiModelProperty("客户端ip")
    private String ip;

    @ApiModelProperty("请求路径")
    @TableField("access_url")
    private String accessUrl;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("日志描述")
    private String description;
    /**
     * 用于区分PC请求与APP请求，便于排查问题
     */
    @ApiModelProperty("日志来源")
    private String src;

    @ApiModelProperty("日志等级，info-一般，warn-警告，error-错误")
    @TableField("log_level")
    private String logLevel;

    @ApiModelProperty("错误信息")
    @TableField("error_info")
    private String errorInfo;

    @ApiModelProperty("请求状态：0-失败，1-成功")
    private boolean succ;

}
