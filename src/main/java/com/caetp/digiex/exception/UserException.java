package com.caetp.digiex.exception;

import com.caetp.digiex.response.ResponseEnum;

/**
 * UserException 用户自定义异常
 *
 */
public final class UserException extends TException {
	public static final UserException PARAM_ISNULL = new UserException(ResponseEnum.PARAM_ISNULL);
	public static final UserException INVALID_PARAMETER = new UserException(ResponseEnum.INVALID_PARAMETER);
	public static final UserException TOKEN_ERROR = new UserException(ResponseEnum.TOKEN_ERROR);
	public static final UserException INVALID_EMAIL = new UserException(ResponseEnum.INVALID_EMAIL);
	public static final UserException INVALID_PASSWORD = new UserException(ResponseEnum.INVALID_PASSWORD);
	public static final UserException WRONG_USERNAME_OR_PASSWORD = new UserException(ResponseEnum.WRONG_USERNAME_OR_PASSWORD);
	public static final UserException USER_NOT_EXIST = new UserException(ResponseEnum.USER_NOT_EXIST);
	public static final UserException USERNAME_EXISTED = new UserException(ResponseEnum.USERNAME_EXISTED);
	public static final UserException USER_FROZEN = new UserException(ResponseEnum.USER_FROZEN);
	public static final UserException USER_LOCKED = new UserException(ResponseEnum.USER_LOCKED);
	public static final UserException USER_LOGIN_PERMISSION_DENIED = new UserException(ResponseEnum.USER_LOGIN_PERMISSION_DENIED);
	public static final UserException OLD_PASSWORD_WRONG = new UserException(ResponseEnum.OLD_PASSWORD_WRONG);
	public static final UserException PASSWORD_WRONG = new UserException(ResponseEnum.PASSWORD_WRONG);
	public static final UserException PERMISSION_DENIED = new UserException(ResponseEnum.PERMISSION_DENIED);
	public static final UserException NEW_PASSWORD_WRONG = new UserException(ResponseEnum.NEW_PASSWORD_WRONG);
	public static final UserException VALIDATE_CODE_WRONG = new UserException(ResponseEnum.VALIDATE_CODE_WRONG);
	public static final UserException NOT_FOUND_RESULT = new UserException(ResponseEnum.NOT_FOUND_RESULT);
	public static final UserException QUERY_RESULT_NOT_UNIQUE = new UserException(ResponseEnum.QUERY_RESULT_NOT_UNIQUE);
    public static final UserException INSUFFICIENT_MARGIN = new UserException(ResponseEnum.INSUFFICIENT_MARGIN);
	public static final UserException REFUSE_OPERATION = new UserException(ResponseEnum.REFUSE_OPERATION);
	public static final UserException CANCEL_ORDER_FAIL = new UserException(ResponseEnum.CANCEL_ORDER_FAIL);
	public static final UserException HAS_UNDEALT_MT5_ORDERS = new UserException(ResponseEnum.HAS_UNDEALT_MT5_ORDERS);
	public static final UserException HAS_USER_ORDER_CANCEL = new UserException(ResponseEnum.HAS_USER_ORDER_CANCEL);
	public static final UserException HAS_USER_ORDER = new UserException(ResponseEnum.HAS_USER_ORDER);
	public static final UserException USER_DELETE = new UserException(ResponseEnum.USER_DELETE);
	public static final UserException USER_DISABLE = new UserException(ResponseEnum.USER_DISABLE);
	public static final UserException FIRST_ORDER_LIMIT = new UserException(ResponseEnum.FIRST_ORDER_LIMIT);

	private static final long serialVersionUID = 1L;

	UserException(ResponseEnum response) {
		super(response);
	}
}
