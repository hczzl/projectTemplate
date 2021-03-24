package com.glch.base.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@Data
public class CoreEntity {
	@TableField(value="id")
	@TableId(value="id",type = IdType.UUID)
	private String id;


}
