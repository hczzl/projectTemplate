package com.glch.log.enums;

import java.util.List;

import com.glch.base.enums.StringEnum;
/*
 * 操作类型
 */
@SuppressWarnings("unchecked")
public class OptTypeEnum extends StringEnum {
	private static final long serialVersionUID = 5374676505458433579L;
	public static final OptTypeEnum LOGIN = new OptTypeEnum("login","登录");   //登录
	public static final OptTypeEnum ADDNEW = new OptTypeEnum("addnew", "新增");   //新增
	public static final OptTypeEnum UPDATE = new OptTypeEnum("update", "修改");    //修改(编辑)
	public static final OptTypeEnum VIEW = new OptTypeEnum("view", "查看");   //查看
	public static final OptTypeEnum DELETE = new OptTypeEnum("delete","删除");    //删除
	public static final OptTypeEnum CHECK = new OptTypeEnum("check","审查");    //审查
	public static final OptTypeEnum AUDIT = new OptTypeEnum("audit","审核");    //审核
	public static final OptTypeEnum APPROVE = new OptTypeEnum("approve","审批");    //审批
	public static final OptTypeEnum QUERY = new OptTypeEnum("query","查询");   //查询
	public static final OptTypeEnum ONE_KEY_QUERY = new OptTypeEnum("oneKeyQuery","一键搜查询");   //查询
	public static final OptTypeEnum DISABLED = new OptTypeEnum("disabled","禁用");    //禁用
	public static final OptTypeEnum ENABLE = new OptTypeEnum("enable","启用");   //启用
	public static final OptTypeEnum APP_THIRD = new OptTypeEnum("thirdAccess","APP第三方调用"); //第三方
	public static final OptTypeEnum EXPORT = new OptTypeEnum("export","导出"); //导出
	public static final OptTypeEnum TASK = new OptTypeEnum("task","定时任务"); //定时任务
	public static final OptTypeEnum ADDORUPDATE = new OptTypeEnum("addOrUpdate","新增或编辑"); //新增或编辑
	public static final OptTypeEnum OTHER = new OptTypeEnum("other","其他");  //其他操作


	private OptTypeEnum(String v, String name) {
		super(v, name);
	}


	public OptTypeEnum() {
		this("", "");
	}

	public static List<OptTypeEnum> getEnumList() {
		return (List<OptTypeEnum>) getEnumList(OptTypeEnum.class);
	}

	public static OptTypeEnum getEnum(String v) {
		return (OptTypeEnum) getEnum(OptTypeEnum.class, v);
	}
}
