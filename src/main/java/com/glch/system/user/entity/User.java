package com.glch.system.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.glch.base.entity.BaseEntity;
import lombok.Data;

import java.sql.Timestamp;

@TableName("t_user")
@Data
public class User extends BaseEntity{
    @TableField(value = "id")
    private String id;

    @TableField(value = "lastLogoutTime")
    private Timestamp lastLogoutTime;

    @TableField(value = "lastLoginTime")
    private Timestamp lastLoginTime;

    @TableField(value = "name")
    private String name;

    @TableField(value = "deptId")
    private String deptId;

    @TableField(value = "position")
    private String position;

    @TableField(value = "loginName")
    private String loginName;

    @TableField(value = "deviceId")
    private String deviceId;

    @TableField(value = "pwd")
    private String pwd;

    @TableField(value = "telephone")
    private String telephone;
}
