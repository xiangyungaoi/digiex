package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("历史详情")
public class UserOrderSellDetailDTO implements BaseDTO {

    @ApiModelProperty("用户下单id")
    private Long id;

    @ApiModelProperty("币种")
    private String aiType;

    @ApiModelProperty("建仓点数")
    private Double buyPrice;

    @ApiModelProperty("平仓点数")
    private Double sellPrice;

    @ApiModelProperty("手数")
    private Double standardHands;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("建仓时间")
    private LocalDateTime buyTime;

    @ApiModelProperty("开仓时间")
    private LocalDateTime sellTime;

    @ApiModelProperty("平仓类型")
    private String type;

    @ApiModelProperty("本金")
    private Double fee;

    @ApiModelProperty("平仓手续费")
    private Double sellServiceFee;

    @ApiModelProperty("库存费")
    private Double inventoryFee;

    @ApiModelProperty("浮动盈亏")
    private Double profit;
}