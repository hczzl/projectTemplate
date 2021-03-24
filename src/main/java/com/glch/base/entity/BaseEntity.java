package com.glch.base.entity;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import com.baomidou.mybatisplus.annotations.TableField;

@Data
public class BaseEntity extends CoreEntity {
    // 创建人
    @TableField(value = "creator")
    @ApiModelProperty(hidden = true)
    private String creator;
    // 创建时间
    @TableField(value = "create_time")
    @ApiModelProperty(hidden = true)
    private Date createTime;
    // 最后修改人
    @TableField(value = "last_update_user")
    @ApiModelProperty(hidden = true)
    private String lastUpdateUser;
    // 最后修改时间
    @ApiModelProperty(hidden = true)
    @TableField(value = "last_update_time")
    private Date lastUpdateTime;
}
