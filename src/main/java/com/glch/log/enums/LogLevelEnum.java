package com.glch.log.enums;

import java.util.List;

import com.glch.base.enums.StringEnum;
/*
 * 日志等级
 */
@SuppressWarnings("unchecked")
public class LogLevelEnum extends StringEnum {
	private static final long serialVersionUID = 5374676505458433579L;
	public static final LogLevelEnum DEBUG = new LogLevelEnum("debug","调试");//运行信息
	public static final LogLevelEnum INFO = new LogLevelEnum("info","强调");//运行的重要信息
	public static final LogLevelEnum WARN = new LogLevelEnum("warn","警告");//提示
	public static final LogLevelEnum ERROR = new LogLevelEnum("error","错误");//发生错误但不影响系统继续运行
	public static final LogLevelEnum FATAL = new LogLevelEnum("fatal","重大错误");//会导致系统无法继续运行
	
	private LogLevelEnum(String v, String name) {
		super(v, name);
	}


	public LogLevelEnum() {
		this("", "");
	}

	public static List<LogLevelEnum> getEnumList() {
		return (List<LogLevelEnum>) getEnumList(LogLevelEnum.class);
	}

	public static LogLevelEnum getEnum(String v) {
		return (LogLevelEnum) getEnum(LogLevelEnum.class, v);
	}
}
