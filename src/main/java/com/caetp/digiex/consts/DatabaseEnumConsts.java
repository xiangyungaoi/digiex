package com.caetp.digiex.consts;

/**
 * 数据库状态枚举常量
 */
public final class DatabaseEnumConsts {
	private DatabaseEnumConsts() {
	}

	/**
	 * Created by wangzy on 2018/7/11.
	 */
	public static enum SAMPLE implements FirstChar {
		ROOT, NARMAL, YES, NO
	}

	public static enum USER_STATE implements FirstChar {
		ROOT, USER
	}
	
	public static enum IS_DELETE implements FirstString {
		YES, NO
	}

	/**
	 * 数据库char型状态存储的枚举类需要实现此接口, enum型不需要
	 */
	private interface FirstChar {
		public String name();

		default public char V() {
			return name().charAt(0);
		}
	}
	
	/**
	 * 数据库char型状态存储的枚举类需要实现此接口, enum型不需要
	 */
	private interface FirstString {
		public String name();

		default public String V() {
			return String.valueOf(name().charAt(0));
		}
	}
}
