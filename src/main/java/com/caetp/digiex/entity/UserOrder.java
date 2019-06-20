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
public class UserOrder implements IEntity {
    private Long id;

    private String orderId;

    private Long aiRobotId;

    private String type;

    private String sellType;

    private Integer memberId;

    private BigDecimal standardHands;

    private BigDecimal pettyCash;

    private BigDecimal fee;

    private BigDecimal buyServiceFee;

    private BigDecimal inventoryFee;

    private BigDecimal sellServiceFee;

    private BigDecimal earnings;

    private Integer status;

    private Boolean isDeleted;

    private LocalDateTime createdTime;

    private LocalDateTime buyTime;

    private LocalDateTime sellTime;

    private LocalDateTime cancelTime;

    private LocalDateTime terminationOrderTime;

    private String location;



}