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
public class Mt5Order implements IEntity {
    private String orderId;

    private Long aiRobotId;

    private String aiType;

    private String orderType;

    private BigDecimal buyPrice;

    private BigDecimal totalStandardHands;

    private BigDecimal totalFee;

    private BigDecimal buyServiceFee;

    private BigDecimal sellPrice;

    private BigDecimal inventoryFee;

    private BigDecimal sellServiceFee;

    private BigDecimal totalEarnings;

    private LocalDateTime buyTime;

    private LocalDateTime sellTime;

    private Integer status;

    private Long createdBy;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

}