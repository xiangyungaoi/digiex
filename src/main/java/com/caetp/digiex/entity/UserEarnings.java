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
public class UserEarnings implements IEntity {
    private Long id;

    private Integer memberId;

    private BigDecimal earnings;

    private LocalDateTime createdTime;

}