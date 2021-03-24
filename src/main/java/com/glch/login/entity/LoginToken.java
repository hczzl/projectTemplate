package com.glch.login.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.glch.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户登录token
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@TableName("t_login_token")
@Data
@ApiModel
public class LoginToken extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    @TableField("login_name")
    private String loginName;
    /**
     * 登录ip
     */
    @ApiModelProperty("登录ip")
    @TableField("login_ip")
    private String loginIp;
    /**
     * 退出时间
     */
    @ApiModelProperty("退出时间")
    @TableField("expire_time")
    private Date expireTime;

}
