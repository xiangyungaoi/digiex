package com.caetp.digiex.dto.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by gaoyx on 2019/5/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户数市账户USD余额")
public class DmemberAccount {

    @ApiModelProperty("USD")
    private String usdBalance;
}
