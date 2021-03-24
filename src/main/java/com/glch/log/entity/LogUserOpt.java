package com.glch.log.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.glch.base.entity.BaseEntity;
import com.glch.log.entity.vo.LogUserOptVo;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

/**
 * <p>
 * 用户操作日志
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@Data
@TableName("t_log_user_opt")
public class LogUserOpt extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 客户端ip
     */
    private String ip;
    /**
     * 授权设备id
     */
    @TableField("device_id")
    private String deviceId;
    /**
     * 请求路径
     */
    @TableField("access_url")
    private String accessUrl;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 操作类型：query-查询，insert-新增，update-更新，delete-删除
     */
    @TableField("opt_type")
    private String optType;
    /**
     * 日志描述
     */
    private String description;
    /**
     * 日志来源，用于区分PC请求与APP请求，便于排查问题
     */
    private String src;
    /**
     * 浏览器名称
     */
    @TableField("browser_name")
    private String browserName;
    /**
     * 浏览器版本号
     */
    @TableField("browser_version")
    private String browserVersion;
    /**
     * 操作系统名称
     */
    @TableField("os_name")
    private String osName;

    /**
     * 请求状态：0-失败，1-成功
     */
    private boolean succ;
    public LogUserOpt copy(LogUserOptVo vo){
        LogUserOpt logUserOpt = new LogUserOpt();
        try {
            BeanUtils.copyProperties(logUserOpt,vo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return logUserOpt;
    }
}
