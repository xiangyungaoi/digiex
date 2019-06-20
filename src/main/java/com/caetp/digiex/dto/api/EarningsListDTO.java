package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * created by zsd on 2019/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("收益明细列表")
public class EarningsListDTO implements BaseDTO{

    @ApiModelProperty("每日收益列表")
    private List<EarningsDTO> list;

    @ApiModelProperty("累计收益")
    private Double totalEarnings;
}
