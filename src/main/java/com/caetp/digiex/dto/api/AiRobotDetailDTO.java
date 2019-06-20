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
@ApiModel("Ai详情")
public class AiRobotDetailDTO implements BaseDTO {

    @ApiModelProperty("AI机器人id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("币种")
    private String aiType;

    @ApiModelProperty("准确率，单位%")
    private Double accuracyRate;

    @ApiModelProperty("盈利订单数，单位笔")
    private Integer profitOrders;

    @ApiModelProperty("平仓收益，单位美元")
    private Double sellEarnings;

    @ApiModelProperty("亏损订单数，单位笔")
    private Integer lossOrders;

    @ApiModelProperty("月盈利率，单位%")
    private Double monthlyProfitRate;

    @ApiModelProperty("最大盈利，单位美元")
    private Double maxProfit;

    @ApiModelProperty("累计交易")
    private Integer totalTransactions;

    @ApiModelProperty("最大亏损，单位美元")
    private Double maxLoss;

    @ApiModelProperty("最大手笔，单位标准手")
    private Double maxStandardHands;

    @ApiModelProperty("平均盈利")
    private Double averageProfit;

    @ApiModelProperty("跟单协议名称")
    private String agreementName;

    @ApiModelProperty("跟单协议")
    private String agreementUrl;

    @ApiModelProperty("AI特征，如'低风险,稳健性,长期持有'，使用','分割")
    private String features;

    @ApiModelProperty("杠杆倍数，如2、10、50")
    private Integer leverageTimes;

    @ApiModelProperty("备用金占比，如1、8")
    private Integer depositRate;

    @ApiModelProperty("本金占比，如1、2")
    private Integer feeRate;
}