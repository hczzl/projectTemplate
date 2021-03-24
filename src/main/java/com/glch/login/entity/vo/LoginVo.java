package com.glch.login.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LoginVo {
    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("密码")
    private String pwd;
}
