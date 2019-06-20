package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangzy on 2019/3/21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("AI跟单配置信息")
public class AiOrderConfigDTO implements BaseDTO {

    @ApiModelProperty("杠杆倍数，如50")
    private String leverageTimes;

    @ApiModelProperty("备用金与本金比，如2:3")
    private String pettyCashRate;
}
