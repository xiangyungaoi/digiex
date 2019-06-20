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
 * created by zsd on 2019/3/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("收益明细")
public class EarningsDTO implements BaseDTO {

    @ApiModelProperty("收益时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("每日收益")
    private Double earnings;

}
