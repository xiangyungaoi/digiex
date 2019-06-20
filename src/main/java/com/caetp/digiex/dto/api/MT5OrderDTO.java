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
@ApiModel("mt5详情")
public class MT5OrderDTO implements BaseDTO {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("总手数")
    private Double totalStandardHands;

    @ApiModelProperty("总金额")
    private Double totalFee;
}