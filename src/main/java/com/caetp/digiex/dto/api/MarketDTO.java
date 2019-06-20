package com.caetp.digiex.dto.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangzy on 2019/2/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("行情")
public class MarketDTO {
    private Long id;

    @ApiModelProperty("币种")
    private String coin;

    @ApiModelProperty("价格")
    private double price;

    @ApiModelProperty("涨幅，单位%")
    private double rate;

    @ApiModelProperty("币种中文名")
    private String coinName;

    @ApiModelProperty("图标url")
    private String coinIcon;

    @ApiModelProperty("币种类型：fx：外汇")
    private String coinType;

    @ApiModelProperty("行情详情url")
    private String detailUrl;

    @ApiModelProperty("是否收藏")
    private boolean isCollect;
}
