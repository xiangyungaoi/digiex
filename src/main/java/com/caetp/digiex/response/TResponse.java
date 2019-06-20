package com.caetp.digiex.response;


public class TResponse extends CustomResponse {

	public TResponse() {
		super();
	}

	public TResponse(Object data) {
		super(data);
	}

	/**
	 * success 常用成功返回方法
	 *
	 * @param data
	 *            需返回对象
	 * @return
	 */
	public static Response success(Object data) {
		return new TResponse(data);
	}

}
