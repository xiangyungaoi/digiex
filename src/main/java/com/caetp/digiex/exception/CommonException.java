package com.caetp.digiex.exception;

import com.caetp.digiex.response.ResponseEnum;

public class CommonException extends TException {

	public static final CommonException UPDATE_FAIL = new CommonException(ResponseEnum.UPDATE_FAIL);
	public static final CommonException SAVE_FAIL = new CommonException(ResponseEnum.SAVE_FAIL);
	public static final CommonException QUERY_RESULT_NOT_UNIQUE = new CommonException(ResponseEnum.QUERY_RESULT_NOT_UNIQUE);
	public static final CommonException REPEAT_OPERATION = new CommonException(ResponseEnum.REPEAT_OPERATION);
	public static final CommonException JSON_FAIL = new CommonException(ResponseEnum.JSON_FAIL);
	public static final CommonException SEND_MESSAGE_FAIL = new CommonException(ResponseEnum.SEND_MESSAGE_FAIL);
	public static final CommonException GET_CODE_FAIL = new CommonException(ResponseEnum.GET_CODE_FAIL);
	public static final CommonException INVALID_PARAMETER = new CommonException(ResponseEnum.INVALID_PARAMETER);
	public static final CommonException REFUSE_OPERATION = new CommonException(ResponseEnum.REFUSE_OPERATION);
	public static final CommonException NOT_FOUND_RESULT = new CommonException(ResponseEnum.NOT_FOUND_RESULT);
	private static final long serialVersionUID = 1L;

	private CommonException(ResponseEnum msg) {
		super(msg);
	}

}
