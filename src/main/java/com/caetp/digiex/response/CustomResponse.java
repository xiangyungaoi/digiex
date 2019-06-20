package com.caetp.digiex.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CustomResponse implements Response {

	// 全局空返回成功请求
	public static final Response SUCCESS = ResponseEnum.SUCCESS;
	private String statusCode;
	private String msg;
	private String statusMsg;
	private Object data;

	public CustomResponse() {
		this.statusCode = ResponseEnum.SUCCESS.getStatusCode();
		this.msg = ResponseEnum.SUCCESS.getMsg();
		this.statusMsg = ResponseEnum.SUCCESS.getStatusMsg();
	}

	protected CustomResponse(Object data) {
		this();
		this.data = data;
	}

	protected CustomResponse(String statusCode, String msg, String statusMsg, Object data) {
		super();
		this.statusCode = statusCode;
		this.msg = msg;
		this.statusMsg = statusMsg;
		this.data = data;
	}

}
