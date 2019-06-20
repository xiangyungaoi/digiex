package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangzy on 2019/2/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户订单信息统计")
public class UserOrderInfoCountDTO implements BaseDTO {
    @ApiModelProperty("保证金比例，单位%")
    private Double depositRate;

    @ApiModelProperty("浮动盈亏，单位$")
    private Double floatProfitLoss;

    @ApiModelProperty("总本金")
    private Double totalFee;

    @ApiModelProperty("可用保证金")
    private Double availableDeposit;

    @ApiModelProperty("已用保证金")
    private Double usedDeposit;

    @ApiModelProperty("已平仓收益")
    private Double closedIncome;

    @ApiModelProperty("收益率")
    private Double incomeRate;

}
