package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * created by zsd on 2019/3/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("交易记录")
public class TransactionRecordDTO implements BaseDTO{

    @ApiModelProperty("机器人名称")
    private String rName;

    @ApiModelProperty("订单类型：'buy'买入、'sell'卖出")
    private String orderType;

    @ApiModelProperty("币种")
    private String aiType;

    @ApiModelProperty("手数")
    private Double standardHands;

    @ApiModelProperty("建仓时间")
    private LocalDateTime buyTime;

    @ApiModelProperty("建仓点数")
    private Double buyPrice;

    @ApiModelProperty("库存费")
    private Double inventoryFee;

    @ApiModelProperty("手续费：buyServiceFee(建仓手续费)+sellServiceFee(平仓手续费)")
    private Double serviceFee;

    @ApiModelProperty("平仓时间")
    private LocalDateTime sellTime;

    @ApiModelProperty("平仓点数")
    private Double sellPrice;

    @ApiModelProperty("收益")
    private Double earnings;


}
