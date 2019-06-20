package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户订单列表")
public class UserOrderListDTO implements BaseDTO {

    @ApiModelProperty("用户下单id")
    private Long id;

    @ApiModelProperty("手数")
    private Double standardHands;

    @ApiModelProperty("本金，单位美元")
    private Double fee;

    @ApiModelProperty("浮动盈亏")
    private Double profit;

    @ApiModelProperty("0待建仓，1持仓中，2待平仓，3已平仓，4已撤销")
    private int status;

    @ApiModelProperty("AI名称")
    private String aiRobotName;

    @ApiModelProperty("AI类型")
    private String aiType;
}