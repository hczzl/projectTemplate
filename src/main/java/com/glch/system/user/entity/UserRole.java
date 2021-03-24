package com.glch.system.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@TableName("t_user_role")
@Data
public class UserRole {
    @TableField(value = "id")
    private String id;

    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "role_id")
    private String roleId;

    @TableField(value = "deparent_id")
    private String deparentId;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "create_time")
    private Date createTime;

}
