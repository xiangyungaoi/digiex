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
@ApiModel("用户订单详情")
public class UserOrderDetailDTO implements BaseDTO {

    @ApiModelProperty("用户下单id")
    private Long id;

    @ApiModelProperty("ai机器人名称")
    private String aiRobotName;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("平仓类型：'user'用户手动平仓，'auto'自动平仓")
    private String sellType;

    @ApiModelProperty("手数")
    private Double standardHands;

    @ApiModelProperty("本金")
    private Double fee;

    @ApiModelProperty("备用金")
    private Double pettyCash;

    @ApiModelProperty("手续费")
    private Double serviceFee;

    @ApiModelProperty("库存费")
    private Double inventoryFee;

    @ApiModelProperty("浮动盈亏或者平仓收益")
    private Double profit;

    @ApiModelProperty("0待建仓，1持仓中，2待平仓，3已平仓，4已撤销")
    private Integer status;

    @ApiModelProperty("建仓时间")
    private LocalDateTime buyTime;

    @ApiModelProperty("平仓时间")
    private LocalDateTime sellTime;

    @ApiModelProperty("撤销时间")
    private LocalDateTime cancelTime;

    @ApiModelProperty("用户跟的AI订单的交易次数")
    private Integer numberOfAiTrade;

    @ApiModelProperty("当前ai交易次数截止的时间")
    private LocalDateTime deadLine;

}