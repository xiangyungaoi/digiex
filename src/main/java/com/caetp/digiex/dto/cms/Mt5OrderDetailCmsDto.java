package com.caetp.digiex.dto.cms;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  author Huzj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("MT5订单详情")
public class Mt5OrderDetailCmsDto implements BaseDTO {

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("币种")
    private String aiType;

    @ApiModelProperty("方向")
    private String orderType;

    @ApiModelProperty("建仓点数")
    private double buyPrice;

    @ApiModelProperty("总手数")
    private double totalStandardHands;

    @ApiModelProperty("总金额")
    private double totalFee;

    @ApiModelProperty("建仓手续费")
    private double buyServiceFee;

    @ApiModelProperty("平仓点数")
    private double sellPrice;

    @ApiModelProperty("库存费")
    private double inventoryFee;

    @ApiModelProperty("平仓手续费")
    private double sellServiceFee;

    @ApiModelProperty("总浮动盈亏/总平仓收益")
    private double totalEarnings;

    @ApiModelProperty("建仓时间")
    private LocalDateTime buyTime;

    @ApiModelProperty("平仓时间")
    private LocalDateTime sellTime;

    @ApiModelProperty("截止时间")
    private LocalDateTime cutoff;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;

}
