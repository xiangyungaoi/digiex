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
public class Mt5UserOrder implements IEntity {
    private String mt5OrderId;

    private Long userOrderId;

    private BigDecimal buyServiceFee;

    private BigDecimal inventoryFee;

    private BigDecimal sellServiceFee;

    private BigDecimal earnings;

    private LocalDateTime sellTime;

}