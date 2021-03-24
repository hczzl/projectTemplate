package com.glch.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页
 * @author wenchaochao
 * @since 2020-04-06
 */
@Data
public class PageEntity {
    @ApiModelProperty(value = "当前页数",example = "1")
    Integer limit;

    @ApiModelProperty(value = "每页记录数",example = "10")
    Integer pageSize;

    @ApiModelProperty(value = "排序列")
    private String orderByColumn;

    @ApiModelProperty(value = "排序方向")
    private String ascOrDesc;
}
