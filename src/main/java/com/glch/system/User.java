package com.glch.system;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
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
}
