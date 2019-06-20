package com.caetp.digiex.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TPage<T> implements BaseDTO {
	private PageInfoDTO pageInfo;
	private long total;
	private List<T> list;

	public TPage(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    public TPage(List<T> list, long total, PageInfoDTO pageInfo) {
        this.list = list;
        this.total = total;
        this.pageInfo = pageInfo;
    }

	/**
	 * emptyList
	 * 返回空List实例,保证List类型的API返回时内容不为null
	 * @return
	 */
	public static <S> TPage<S> emptyList() {
	    return new TPage<>(new ArrayList<>(), 0);
	}

	public static <S> TPage<S> of(List<S> list, long total) {
	    return new TPage<>(list, total);
	}

	public static <S> TPage<S> of(List<S> list, long total, PageInfoDTO pageInfo) {
	    return new TPage<>(list, total, pageInfo);
	}

}
