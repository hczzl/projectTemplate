package com.glch.log.entity.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.glch.base.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogUserOptVo extends PageEntity {
    @ApiModelProperty("客户端ip")
    private String ip;

    @ApiModelProperty("授权设备id")
    @TableField("device_id")
    private String deviceId;

    @ApiModelProperty("请求路径")
    @TableField("access_url")
    private String accessUrl;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("操作类型：query-查询，insert-新增，update-更新，delete-删除")
    @TableField("opt_type")
    private String optType;

    @ApiModelProperty("日志描述")
    private String description;

    @ApiModelProperty("日志来源")
    private String src;

    @ApiModelProperty("浏览器名称")
    @TableField("browser_name")
    private String browserName;

    @ApiModelProperty("浏览器版本号")
    @TableField("browser_version")
    private String browserVersion;

    @ApiModelProperty("操作系统名称")
    @TableField("os_name")
    private String osName;

    @ApiModelProperty("请求状态：0-失败，1-成功")
    private boolean succ;
}
