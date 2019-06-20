package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiRobot implements IEntity {
    private Long id;

    private String name;

    private Integer leverageTimes;

    private Integer depositRate;

    private Integer feeRate;

    private BigDecimal stopLossLimit;

    private String features;

    private String aiType;

    private String aiTypeName;

    private BigDecimal accuracyRate;

    private Integer profitOrders;

    private Integer lossOrders;

    private BigDecimal sellEarnings;

    private BigDecimal monthlyProfitRate;

    private BigDecimal maxProfit;

    private BigDecimal maxLoss;

    private Integer totalTransactions;

    private BigDecimal maxStandardHands;

    private BigDecimal averageProfit;

    private Boolean isActivated;

    private Boolean isDeleted;

    private String agreementName;

    private String agreementUrl;

    private Long createdBy;

    private LocalDateTime createdTime;

    private Long updatedBy;

    private LocalDateTime updatedTime;

}