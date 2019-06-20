package com.caetp.digiex.dto.cms;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户订单Dto
 * author Huzj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("MT5订单的用户订单列表")
public class UserOrderMT5CmsDto implements BaseDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户id")
    private Integer memberId;

    @ApiModelProperty("本金")
    private BigDecimal fee;

    @ApiModelProperty("备用金")
    private BigDecimal pettyCash;

    @ApiModelProperty("收益")
    private BigDecimal earnings;

    @ApiModelProperty("手数")
    private BigDecimal standardHands;

    @ApiModelProperty("库存费")
    private BigDecimal inventoryFee;

    @ApiModelProperty("平仓手续费")
    private BigDecimal sellServiceFee;

    @ApiModelProperty("0待建仓，1持仓中，2待平仓，3已平仓，4已撤销")
    private Integer status;

    @ApiModelProperty("ai的止损限制条件：剩余保证金 / 初始保证金")
    private BigDecimal stopLossLimit;
}
