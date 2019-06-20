package com.caetp.digiex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by wangzy on 2018/11/13.
 */
@Data
@AllArgsConstructor
public class PageInfoDTO implements BaseDTO {
    // 总数据条数
    private int totalRow;

    // 页码
    private int pageNumber;

    // 总页数
    private int totalPage;

    // 页大小
    private int pageSize;
}
