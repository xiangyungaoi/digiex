package com.caetp.digiex.utli.common;

import org.springframework.beans.BeanUtils;

import java.util.List;

public class EntityToPOJO {

	/**
	 * 用于转换entity列表为POJO列表 注意POJO与Entity相同参数名必须对应
	 *
	 * @param dest 目标POJO列表
	 * @param clazz 目标POJO类型
	 * @param orig 原Entity列表
	 */
	public static <S> void entityListConvert(List<S> dest, Class<S> clazz, List<?> orig) {
		orig.forEach(d -> {
			S s = null;
			try {
				s = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				System.err.println("目标POJO对象无空构造函数或新建实例失败,请检查调试!");
				throw new RuntimeException();
			}
			try {
				BeanUtils.copyProperties(d,s);
			} catch (Exception e) {
				System.err.println("Entity转换失败!");
				e.printStackTrace();
				throw new RuntimeException();
			}
			if (s != null)
				dest.add(s);
		});
	}

	/**
	 * 用于转换单个Entity至对应POJO
	 *
	 * @param dest 目标POJO
	 * @param orig 原Entity
	 */
	public static void entityConvert(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(orig,dest);
		} catch (Exception e) {
			System.err.println("Entity转换失败!");
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
