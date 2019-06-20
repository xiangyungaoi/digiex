package com.caetp.digiex.consts;

import java.time.format.DateTimeFormatter;

/**
 * ProjectConsts 项目常量
 */

public final class ProjectConsts {
	// 中横线
	public static final String HYPHEN = "-";
	// 下横线
	public static final String UNDER_LINE = "_";
	// 左斜线
	public static final String LEFT_SLANT = "/";
	// 竖线
	public static final String VERTICAL_LINE = "|";
	// 逗号
	public static final String COMMA = ",";
	// 又斜线
	public static final String RIGHT_SLANT = "\\";
	// 默认时区
	public static final String DEFAULT_TIMEZONE = "GMT+8";
	public static final String DEFAULT_TIMEZONE_NAME = "Asia/Shanghai";
	// 时间统一格式
	public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	// 日期统一格式
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	// 日期统一格式
	public static final String DATE_PATTERN = "yyyyMMdd";
	// API页面默认大小
	public static final int PAGE_SIZE = 15;
	// 文件目录
	public static String FILE = "/file/";

    //方向 买入
    public static final String DB_BUY = "buy";
    //方向 卖出
    public static final String DB_SELL = "sell";

	// 订单类型:买入
	public static final String DB_BUY1 = "0";
	// 订单类型:卖出
	public static final String DB_SELL1 = "1";
	// 订单类型:回踩买入
	public static final String DB_BUY_LIMIT = "2";
	// 订单类型:回踩卖出
	public static final String DB_SELL_LIMIT = "3";
	// 订单类型:突破买入
	public static final String DB_BUY_STOP = "4";
	// 订单类型:突破卖出
	public static final String DB_SELL_STOP = "5";
	// 订单类型:突破回踩买入
	public static final String DB_BUY_STOP_LIMIT = "6";
	// 订单类型:突破回踩卖出
	public static final String DB_SELL_STOP_LIMIT = "7";
	// 订单类型:OP_CLOSE_BY 意义暂时不明
	public static final String DB_OP_CLOSE_BY = "8";

	//方向 买入
    public static final String BUY = "买入";
    //方向 卖出
    public static final String SELL = "卖出";
	// 阿里云实名认证
	public static final String IDEN_AUTHEN_URL = "http://aliyunverifyidcard.haoservice.com/idcard/VerifyIdcardv2?realName={0}&cardNo={1}";
	public static final String AUTHORIZATION = "Authorization";
	public static final String AUTHORIZATION_HEADER = "APPCODE {0}";
	public static final String RESULT = "result";
	public static final String IS_OK = "isok";


	private ProjectConsts() {
	}

}
