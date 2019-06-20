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
@ApiModel("Ai列表")
public class AiRobotListDTO implements BaseDTO {

    @ApiModelProperty("AI机器人id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("币种")
    private String aiType;

    @ApiModelProperty("准确率，单位%")
    private Double accuracyRate;

    @ApiModelProperty("平仓收益，单位美元")
    private Double sellEarnings;

    @ApiModelProperty("AI特征，如'低风险,稳健性,长期持有'，使用','分割")
    private String features;
}