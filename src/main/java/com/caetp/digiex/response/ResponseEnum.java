package com.caetp.digiex.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

/**
 * Responses 含:无数据成功返回,异常返回

 *
 */
@JsonFormat(shape = Shape.OBJECT)
@Getter
public enum ResponseEnum implements Response {

	// ================================成功请求[00]==============================
	SUCCESS("00", "Success", "请求成功"),

	// ================================系统异常[10-39]==============================
	// ------------------未知异常[10]-------------------
	SERVER_UNKNOWN_ERROR("10", "Server Unknown Error", "服务器出现未知错误"),

	// -----------------服务器异常[11-19]-------------------
	NULL_POINTER_EXCEPTION("11", "Server NullPointerException", "服务器空指针异常"),

	// ------------------数据库异常[20-29]------------------
	INSERT_DUPLICATE("20", "Database Insert Duplicate", "数据库插入重复"),
	UPDATE_DUPLICATE("21", "Database Update Duplicate", "数据库更新重复"),
	DATA_INTEGRITY_VIOLATION_EXCEPTION("22", "Data Integrity Violation", "违反数据完整性"),

	// ------------------参数异常[30-39]--------------------
	INVALID_PARAMETER("30", "Invalid Parameter", "无效参数,请检查传入参数"),

	// ===============================自定义异常[40-99]=============================
	// ===============================通用异常[40-59]===============================
	UPDATE_FAIL("40", "Update Fail", "更新失败"),
	SAVE_FAIL("41", "Save Fail", "保存失败"),
	REPEAT_OPERATION("42", "Repeat Operation", "请勿重复操作"),
	JSON_FAIL("43", "Json Parse Fail", "Json解析失败"),
	QUERY_RESULT_NOT_UNIQUE("44", "Query Result Not Unique", "数据库查询值不唯一"),
	REFUSE_OPERATION("45", "Refuse Operation", "拒绝操作"),
	NOT_FOUND_RESULT("46", "not found result", "没有查询到结果"),
	SEND_MESSAGE_FAIL("47", "Send Message Fail", "消息发送失败"),
	GET_CODE_FAIL("48", "Get The Verifying Code Fail", "获取验证码失败"),
	VALIDATE_CODE_WRONG("49", "The Verifying Code Is Error", "验证码错误或失效"),
	FILE_TOO_LARGE("50", "The file is too large", "文件大小超过限制"),
    INSUFFICIENT_MARGIN("51", "Insufficient margin", "Digi Wallet 中的美元账户余额不足，请及时充值"),
	
	// ===============================用户操作异常[60-89]===============================
	// ------------------用户权限[60-69]------------------
	TOKEN_ERROR("60", "Token Error", "认证失败,请重新登录"),
	PERMISSION_DENIED("61", "Permission Denied", "您无此操作权限"),
	USER_FROZEN("62", "User Frozen", "用户已冻结"),
	USER_LOCKED("63", "User Is Locked", "该账号已被锁定"),
	USER_LOGIN_PERMISSION_DENIED("64", "User Login Permission Denied", "此APP只对VIP及以上用户开放，请您先到Digi CRM进行升级"),

	// ------------------用户密码[70-79]------------------
	OLD_PASSWORD_WRONG("70", "Wrong Old Password", "旧密码错误"),
	PASSWORD_WRONG("71", "Wrong Password", "密码错误"),
	INVALID_PASSWORD("72", "Invalid Password", "密码必须含大小写及数字"),
	NEW_PASSWORD_WRONG("73", "New Password Wrong", "两次密码填写不一致"),

	// ------------------用户存在判断[80-89]------------------
	USERNAME_EXISTED("80", "Username Already Existed", "该用户名已经存在"),
	USER_NOT_EXIST("81", "User Not Exist", "该用户不存在"),
	INVALID_EMAIL("82", "Invalid Email", "邮箱格式不正确"),
	WRONG_USERNAME_OR_PASSWORD("83", "Wrong Username or Password", "账号或密码有误，连续4次错误后，账号将被锁定一个小时"),
	PARAM_ISNULL("84", "username or password is null", "用户名或密码为空"),
	USER_DISABLE("85","User Disable","用户已被禁用"),
	USER_DELETE("86","User Delete","用户已被删除"),

	// ------------------用户存在判断[90-99]------------------
	CANCEL_ORDER_FAIL("90", "Cancel Order Fail", "撤销失败，订单状态已改变，请刷新重试"),
	HAS_UNDEALT_MT5_ORDERS("91", "Has Undealt Mt5 Orders", "当前AI机器人下存在未平仓的订单，请先平仓后再建仓！"),
	HAS_USER_ORDER_CANCEL("92", "Has User Order Cancel", "建仓失败，由于有用户订单撤销，请刷新后再操作！"),
	HAS_USER_ORDER("93","Has User Order","当前AI机器人存在订单，无法删除！"),
	FIRST_ORDER_LIMIT("94","The first order has limit","首次跟单美元账户最低为$1000，请充值后再跟单！"),

	// 末尾分号
	;

	private static final ObjectMapper mapper = new ObjectMapper();
	private String statusCode;
	private String msg;
	private String statusMsg;
	private Object data;
	private String[] stackTrace;

	ResponseEnum(String statusCode, String msg, String statusMsg) {
		this.statusCode = statusCode;
		this.msg = msg;
		this.statusMsg = statusMsg;
	}

	/**
	 * main 打印全部列表,用于API文档表格生成
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("表格部分=================================");
		for (ResponseEnum e : ResponseEnum.values()) {
			System.out.println(e.getStatusCode() + "|" + e.getStatusMsg() + "|" + e.getMsg());
		}
		System.out.println("详情部分=================================");
		for (ResponseEnum e : ResponseEnum.values()) {
			System.out.println("<br>" + e.getStatusCode() + "=" + e.getStatusMsg());
		}

	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setStackTrace(String[] stackTrace) {
		this.stackTrace = stackTrace;
	}

	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			System.err.println("异常参数Json化失败!");
			throw new RuntimeException();
		}
	}
}
