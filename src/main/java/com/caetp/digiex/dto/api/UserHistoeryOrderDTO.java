package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by gaoyx on 2019/6/3.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户订单历史")
public class UserHistoeryOrderDTO implements BaseDTO{
    @ApiModelProperty("ai机器人id. ai跟单:ai机器人id.   平仓订单，挂单:无")
    private Long aiRobotId;


    @ApiModelProperty("订单类型: ai跟单，平仓订单，挂单")
    private String tradeType;

    @ApiModelProperty("名称. ai跟单:ai机器人名称. 平仓订单和挂单:货币英文名称")
    private String name;

    @ApiModelProperty("货币中文名称.  ai跟单:无.  平仓订单和挂单:货币中文名称")
    private String symbolName;

    @ApiModelProperty("订单号. ai跟单:无.  平仓订单,挂单:订单号")
    private Integer orderId;

    @ApiModelProperty("手数.  ai跟单,平仓订单,挂单:手数")
    private Double standardHands;

    @ApiModelProperty("订单交易方向. ai跟单:无. 平仓订单: OP_BUY =0, OP_SELL =1. 挂单:P_BUY_LIMIT =2, OP_SELL_LIMIT =3,  OP_BUY_STOP =4,  OP_SELL_STOP =5,  OP_BUY_STOP_LIMIT =6, OP_SELL_STOP_LIMIT =7")
    private Integer tradeCmd;

    @ApiModelProperty("盈利. ai跟单,平仓订单:平仓盈利. 挂单:无")
    private Double profit;

    @ApiModelProperty("状态: ai跟单:3已终止，4已撤销. 平仓订单:5已平仓. 挂单:6已取消")
    private Integer status;

    @ApiModelProperty("价格1. ai跟单:无. 平仓订单:建仓价位.  挂单:挂单价位")
    private Double startPrice;

    @ApiModelProperty("价格2. ai跟单:无. 平仓订单:平仓价位. 挂单:无")
    private Double endPrice;

    @ApiModelProperty("ai:终止跟单时间. 平仓订单:平仓时间  挂单:撤销挂单时间")
    private LocalDateTime time;


}