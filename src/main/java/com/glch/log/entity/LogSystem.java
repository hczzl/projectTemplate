package com.glch.log.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.glch.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import com.glch.log.entity.vo.LogSystemVo;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@Data
@TableName("t_log_system")
public class LogSystem extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
    public LogSystem copy(LogSystemVo vo){
        LogSystem logSystem = new LogSystem();
        try {
            BeanUtils.copyProperties(logSystem,vo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return logSystem;
    }

}
