package com.glch.base.common;



public class BizException extends Exception {
	
	private Object exInfo;
	
	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public Object getExInfo() {
		return exInfo;
	}

	public void setExInfo(Object exInfo) {
		this.exInfo = exInfo;
	}
	
}
