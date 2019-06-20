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
@ApiModel("用户订单列表")
public class UserOrderCmsDto implements BaseDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名称")
    private String nickName;

    @ApiModelProperty("用户手机号码")
    private String mobile;

    @ApiModelProperty("下单时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("手数")
    private double standardHands;

    @ApiModelProperty("建仓手续费")
    private BigDecimal buyServiceFee;

    @ApiModelProperty("金额")
    private double fee;

    @ApiModelProperty("平仓收益")
    private double earnings;

    @ApiModelProperty("0待建仓，1持仓中，2待平仓，3已平仓，4已撤销")
    private Integer status;
}
