package com.caetp.digiex.dto.cms;

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
@ApiModel("AI管理")
public class AiRobotCmsDto implements BaseDTO{

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("AI名称")
    private String name;

    @ApiModelProperty("币种类型")
    private String aiType;

    @ApiModelProperty("币种中文名称")
    private String aiTypeName;

    @ApiModelProperty("协议名字")
    private String agreementName;

    @ApiModelProperty("协议url")
    private String agreementUrl;

    @ApiModelProperty("是否开启")
    private Boolean isActivated;

    @ApiModelProperty("杠杆倍数")
    private Integer leverageTimes;

    @ApiModelProperty("保证金占比")
    private Integer depositRate;

    @ApiModelProperty("本金占比")
    private Integer feeRate;

    @ApiModelProperty("止损限制条件，百分数")
    private Integer stopLossLimit;

    @ApiModelProperty("AI标签，如'低风险,稳健性,长期持有'，使用','拼接'")
    private String features;

}
