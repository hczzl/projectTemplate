package com.glch.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

@Data
@TableName("T_BASE_MODULE")
public class BaseModuleEntity {
	@TableField(value="FID")
	@TableId(value="FID",type = IdType.INPUT)
	private String id;
	
	@TableField(value="FID")
	private String c;
}
