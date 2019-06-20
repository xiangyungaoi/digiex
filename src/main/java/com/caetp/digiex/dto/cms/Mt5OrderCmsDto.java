package com.caetp.digiex.dto.cms;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 *  author Huzj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Mt5订单列表")
public class Mt5OrderCmsDto implements BaseDTO {

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("币种")
    private String aiType;

    @ApiModelProperty("方向")
    private String orderType;

    @ApiModelProperty("建仓点数")
    private Double buyPrice;

    @ApiModelProperty("建仓点数")
    private Double buyServiceFee;

    @ApiModelProperty("总手数")
    private Double totalStandardHands;

    @ApiModelProperty("总金额")
    private Double totalFee;

    @ApiModelProperty("平仓点数")
    private Double sellPrice;

    @ApiModelProperty("总浮动盈亏/总平仓收益")
    private Double totalEarnings;

    @ApiModelProperty("截止时间")
    private LocalDateTime cutoff;

    @ApiModelProperty("建仓时间")
    private LocalDateTime buyTime;

    @ApiModelProperty("平仓时间")
    private LocalDateTime sellTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;
}
